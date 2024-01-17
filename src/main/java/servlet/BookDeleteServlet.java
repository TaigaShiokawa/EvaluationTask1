package servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BookDAO;

@WebServlet("/BookDeleteServlet")
public class BookDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		try {
			BookDAO bDao = new BookDAO();
			
			String jan = request.getParameter("jan");
			
			int row = bDao.deleteBook(jan);
			if (row != 1) {
				request.getSession().setAttribute("deleteError", "削除に失敗しました。");
				response.sendRedirect("BookEditServlet?jan=" + jan);
				return;
			} else {
				response.sendRedirect("BookListServlet");
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "クラスが見つかりませんでした。");
	        response.sendRedirect("error.jsp");
	        return;
		} catch(SQLException e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "現在データベースにアクセスできません。");
	        response.sendRedirect("error.jsp");
	        return;
		} catch(Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "システムエラーが発生しました。");
	        response.sendRedirect("error.jsp");
	        return;
		}
	}

}
