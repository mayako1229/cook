package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Cook;

public class CookDAO {

	//取得
	public List<Cook> findByDate(String year, String month) {
		List<Cook> oookList = new ArrayList<Cook>();
		
		// DBに接続 	
		try (Connection conn = DBManager.getConnection();) {
			String sql = "SELECT id,cook_date, menu_title, detail FROM cook WHERE YEAR(cook_date)=? AND MONTH(cook_date)=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(year));
			ps.setInt(2, Integer.parseInt(month));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				java.sql.Date sqlDate = rs.getDate("cook_date"); // SQLの日付型を取得
				LocalDate cookDate=null;
				if (sqlDate != null) {
					cookDate = sqlDate.toLocalDate(); // LocalDateに変換
				}
				Cook cook = new Cook(id, cookDate, rs.getString("menu_title"), rs.getString("detail"));
				oookList.add(cook);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return oookList;
	}

	//登録
	public boolean create(Cook c) { 
    	// DBに接続 
        try (Connection conn = DBManager.getConnection(); ){
            String sql = "INSERT INTO cook(cook_date, menu_title, detail) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(c.getCookDate()));
            ps.setString(2, c.getMenuTitle());
            ps.setString(3, c.getDetail());
            ps.executeUpdate();
            ps.close();
            conn.close();

            
        }catch(SQLException e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
	
	//変更
	public boolean modify(Cook c) { 
    	// DBに接続 
        try (Connection conn = DBManager.getConnection(); ){
            //String sql = "UPDATE cook SET cook_date=?, menu_title=?, detail=? WHERE id = ?";
            String sql = "UPDATE cook SET  menu_title=?, detail=? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setDate(1, java.sql.Date.valueOf(c.getCookDate()));
            ps.setString(1, c.getMenuTitle());
            ps.setString(2, c.getDetail());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();

            
        }catch(SQLException e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
	
	//削除
	public boolean delete(int id) { 
    	// DBに接続 
        try (Connection conn = DBManager.getConnection(); ){
            String sql = "DELETE FROM cook WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            conn.close();

            
        }catch(SQLException e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
	
	public Cook findByDate(LocalDate date) {
	    // DBに接続     
	    Cook cook = null;
	    try (Connection conn = DBManager.getConnection();) {
	        // SQL文
	        String sql = "SELECT id, cook_date, menu_title, detail FROM cook WHERE cook_date=?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        
	        ps.setDate(1, java.sql.Date.valueOf(date));
	        
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            int id = rs.getInt("id");
	            java.sql.Date sqlDate = rs.getDate("cook_date"); // SQLの日付型を取得
	            LocalDate cookDate = null;
	            if (sqlDate != null) {
	                cookDate = sqlDate.toLocalDate(); // LocalDateに変換
	            }
	            cook = new Cook(id, cookDate, rs.getString("menu_title"), rs.getString("detail"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null; // 例外発生時はnullを返す
	    }
	    return cook;
	}
	
	public boolean createList(List<Cook> list) {
	    boolean success = true;
	    Connection conn = null; 

	    try {
	        // 1. Connectionを取得し、try-with-resourcesで自動クローズを設定
	        conn = DBManager.getConnection();
	        
	        // 2. オートコミットを無効にし、トランザクション開始
	        conn.setAutoCommit(false); 
	        
	        // 3. PreparedStatementもtry-with-resourcesで自動クローズを設定
	        String sqlCreate = "INSERT INTO cook(cook_date, menu_title, detail) VALUES (?,?,?)";
	        String sqlModify = "UPDATE cook SET menu_title=?, detail=? WHERE id = ?";
	        
	        // try-with-resources内に複数のリソースを記述
	        try (PreparedStatement psCreate = conn.prepareStatement(sqlCreate); 
	             PreparedStatement psModify = conn.prepareStatement(sqlModify)) {

	            // 4. リストをループし、処理を実行
	            for (Cook c : list) {
	                // 日付で検索 (トランザクション外で実行しても問題なし)
	                LocalDate date = c.getCookDate();
	                Cook existingCook = findByDate(date); 

	                if (existingCook == null) {
	                	System.out.println(c.getCookDate() + ":create");
	                    // 存在しなかったら登録 (CREATE)
	                    psCreate.setDate(1, java.sql.Date.valueOf(c.getCookDate()));
	                    psCreate.setString(2, c.getMenuTitle());
	                    psCreate.setString(3, c.getDetail());
	                    psCreate.executeUpdate();
	                    
	                } else {
	                	System.out.println(c.getCookDate() + ":modify");
	                    // 存在したら更新 (MODIFY)
	                    c.setId(existingCook.getId()); 
	                    
	                    psModify.setString(1, c.getMenuTitle());
	                    psModify.setString(2, c.getDetail());
	                    psModify.setInt(3, c.getId());
	                    psModify.executeUpdate();
	                }
	            }

	            // 5. すべて成功したらコミット
	            conn.commit(); 

	        } // PreparedStatementがここで自動的にクローズされる
	        
	    } catch (SQLException e) {
	        // 6. 失敗したらロールバック
	        success = false;
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	    }
	    
	    return success;
	}
}
