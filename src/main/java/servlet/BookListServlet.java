package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BookDAO;
import model.dto.BookDTO;

@WebServlet("/BookListServlet")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getSession().setMaxInactiveInterval(30);
		
		List<BookDTO> bookList = new ArrayList<>();
		BookDAO bDao = new BookDAO();
		try {
			bookList = bDao.getAllBooks();
			if(bookList == null || bookList.isEmpty()) {
				request.setAttribute("error", "エラー");
			} else {
				request.setAttribute("bookList", bookList);
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
		
		request.getRequestDispatcher("book-list.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	}

}
