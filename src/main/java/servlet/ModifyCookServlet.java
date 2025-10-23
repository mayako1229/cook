package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import logic.CookLogic;
import model.Cook;

@WebServlet("/modify")
public class ModifyCookServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// JSON受け取り
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = request.getReader()) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		}
		//JSONで受け取ったデータを表示（確認用）
		//        System.out.println("data" + sb.toString());
		try {
			//JSONObjectを生成（libにjson-20250517.jarを置く必要あり）
			JSONObject json = new JSONObject(sb.toString());
			int id = json.getInt("id");
			String title = json.getString("title");
			String detail = json.getString("detail");
			String year = String.valueOf(json.getInt("year"));
			String month = String.valueOf(json.getInt("month"));

			response.setContentType("application/json;charset=UTF-8");
			
			JSONObject jsonRes;

			CookLogic cl = new CookLogic();
			Cook cook = new Cook(id, null, title, detail);

			boolean result = cl.modifyCook(cook);
			jsonRes = cl.getCookJsonList(year, month);

			PrintWriter out = response.getWriter();
			out.print(jsonRes.toString());
			out.close();

			if (!result) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		}
		response.setStatus(200);

	}
}
