package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BookDAO;

@WebServlet("/BookRegisterServlet")
public class BookRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try {
			BookDAO bDao = new BookDAO();
			
			HashMap<String, String> errorMessages = new HashMap<>();

			String jan = request.getParameter("jan");
			String isbn = request.getParameter("isbn");
			String bookName = request.getParameter("bookName");
			String bookKana = request.getParameter("bookKana");
			int price = Integer.parseInt(request.getParameter("price"));
			Date issueDate = Date.valueOf(request.getParameter("issueDate"));
			LocalDateTime createDate = LocalDateTime.of(LocalDate.parse(request.getParameter("createDate")),LocalTime.now());
			
			boolean janCodeExist = bDao.janCodeExist(jan);
			if(janCodeExist) {
				errorMessages.put("janError", "そのJANコードはすでに登録されています。");
			}
			
			if(jan.length() != 13) {
				errorMessages.put("janError", "JANコードは13桁で登録してください。");
			}
			
			if(isbn.length() != 13) {
				errorMessages.put("isbnError", "ISBNコードは13桁で登録してください。");
			}
			
			if(price < 0) {
				errorMessages.put("priceError", "「価格」が設定されていません。");
			}
			
			if(bookName == null || bookName.isEmpty()) {
				errorMessages.put("bookNameError", "「書籍名」が設定されていません。");
			}
			
			if(bookKana == null || bookKana.isEmpty()) {
				errorMessages.put("bookKanaError", "「書籍名カナ」が設定されていません。");
			}
			
			saveFormDataInSession(request, jan, isbn, bookName, bookKana, price, issueDate, createDate);

			
			if(!errorMessages.isEmpty()) {
				request.getSession().setAttribute("registerError", errorMessages);
				response.sendRedirect("BookListServlet");
				return;
			}
			
			int row = bDao.registerBook(jan, isbn, bookName, bookKana, price, issueDate, createDate);
			if (row != 1) {
				request.setAttribute("failure", "登録に失敗しました。");
				request.getRequestDispatcher("book-edit.jsp").forward(request, response);
			} else {
				response.sendRedirect("BookListServlet");
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
	}
	
	private void saveFormDataInSession(HttpServletRequest request, String jan, String isbn, String bookName, String bookKana, 
	        int price, Date issueDate, LocalDateTime createDate) {
	    request.getSession().setAttribute("jan", jan);
	    request.getSession().setAttribute("isbn", isbn);
	    request.getSession().setAttribute("bookName", bookName);
	    request.getSession().setAttribute("bookKana", bookKana);
	    request.getSession().setAttribute("price", price);
	    request.getSession().setAttribute("issueDate", issueDate);
	    request.getSession().setAttribute("createDate", createDate);
	}


}
