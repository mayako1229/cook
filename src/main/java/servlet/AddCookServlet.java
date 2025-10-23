package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.CookLogic;
import model.Cook;

@WebServlet("/add")
public class AddCookServlet extends HttpServlet {
	//doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("cook_form.jsp");
		dispatcher.forward(request, response);
	}

	//doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // "cook_date"という名前で送信されたすべての値を取得
        String[] cookDates = request.getParameterValues("cook_date");
        
        // "menu_title"という名前で送信されたすべての値を取得
        String[] menuTitles = request.getParameterValues("menu_title");
        
        // "detail"という名前で送信されたすべての値を取得
        String[] details = request.getParameterValues("detail");
        
        // 登録されたメニューの数
        int menuCount = cookDates != null ? cookDates.length : 0;
        
        List<Cook> cookList = new ArrayList<>();
        // ループで処理
        if (menuCount > 0) {
            for (int i = 0; i < menuCount; i++) {
                String date = cookDates[i];
                String title = menuTitles[i];
                String detail = details[i];
        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        		LocalDate cookDate = LocalDate.parse(date, formatter);
                Cook cook = new Cook(0, cookDate, title, detail);
                cookList.add(cook);
            }
        }
		CookLogic cl = new CookLogic();
		
		if (!cl.createCooks(cookList)) {
			request.setAttribute("msg", "登録に失敗しました");
			RequestDispatcher dispatcher = request.getRequestDispatcher("cook_form.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("./");
		}
	}
}
