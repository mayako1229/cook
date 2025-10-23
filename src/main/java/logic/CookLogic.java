package logic;

import java.util.List;

import org.json.JSONObject;

import dao.CookDAO;
import model.Cook;

public class CookLogic {
	CookDAO dao = new CookDAO();

	//料理の登録
	public boolean createCook(Cook c){
		if(c==null) {
			return false;
		}
		return dao.create(c);
	}
	
	//料理の登録(複数)
	public boolean createCooks(List<Cook> list){
		if(list==null) {
			return false;
		}
		return dao.createList(list);
	}
	
	//料理一覧の取得
	public List<Cook> getCookList(String y,String m){
		return dao.findByDate(y,m);
	}
	
	//料理の編集
	public boolean modifyCook(Cook c){
		if(c==null|| c.getId()==0) {
			return false;
		}
		return dao.modify(c);
	}

	//料理の削除
	public boolean deleteCook(int id){
		if(id==0) {
			return false;
		}
		return dao.delete(id);
	}
	
	//料理一覧をJSON形式で返却
	public JSONObject getCookJsonList(String y,String m){
        JSONObject json = new JSONObject();
        List<Cook> oookList = getCookList(y, m);
        if (oookList!=null) {
            for(Cook c : oookList){
                String date = c.getCookDate().toString();
                JSONObject item = new JSONObject();
                item.put("id", c.getId());
                item.put("title", c.getMenuTitle());
                item.put("detail", c.getDetail());
                json.put(date, item);
            }
        
        }
        return json;
	}
}
