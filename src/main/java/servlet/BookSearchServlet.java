package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BookDAO;
import model.dto.BookDTO;

@WebServlet("/BookSearchServlet")
public class BookSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			BookDAO bDao = new BookDAO();
			
			List<BookDTO> searchBookList = new ArrayList<>();
			String title = request.getParameter("title");
			String kana = request.getParameter("kana");
			String priceStr = request.getParameter("price");
			Integer price = null;
			
			if (priceStr != null && !priceStr.isEmpty()) {
				price = Integer.parseInt(priceStr);
			}

			searchBookList = bDao.searchForBooks(title, kana, price);
			if(searchBookList == null || searchBookList.isEmpty()) {
				request.setAttribute("notFound", "検索結果がありませんでした。");
			} else {
				request.setAttribute("bookList", searchBookList);
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "入力された値が数値ではありません。数値のみを入力してください。");
	        response.sendRedirect("error.jsp");
	        return;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "入力された値が無効です。指定された範囲または形式の値を入力してください。");
	        response.sendRedirect("error.jsp");
	        return;
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "入力された日付または時刻の形式が正しくありません。正しい形式で入力してください。");
	        response.sendRedirect("error.jsp");
	        return;
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

}
