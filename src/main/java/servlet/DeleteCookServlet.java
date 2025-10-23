package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import logic.CookLogic;

@WebServlet("/delete")
public class DeleteCookServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        System.out.println(idStr);
        System.out.println(year);
        System.out.println(month);
        
        response.setContentType("application/json;charset=UTF-8");
        JSONObject json;
		try {
			int id = Integer.parseInt(idStr);
			CookLogic cl = new CookLogic();
			boolean result = cl.deleteCook(id);
	        json =  cl.getCookJsonList(year, month);
	        PrintWriter out = response.getWriter();
	        out.print(json.toString());
	        out.close();
	        
			if(!result) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}
		response.setStatus(200);
		

	}
}
