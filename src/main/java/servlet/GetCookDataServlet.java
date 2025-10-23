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

@WebServlet("/getCookData")
public class GetCookDataServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String year = request.getParameter("year");
        String month = request.getParameter("month");

        response.setContentType("application/json;charset=UTF-8");
        CookLogic cl = new CookLogic();
        JSONObject json =  cl.getCookJsonList(year, month);
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.close();
    }
}
