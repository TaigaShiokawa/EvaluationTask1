package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BookDAO;
import model.dto.BookDTO;

@WebServlet("/BookEditServlet")
public class BookEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BookDTO book = new BookDTO();
		BookDAO bDao = new BookDAO();

		String jan = request.getParameter("jan");
		try {
			book = bDao.getBook(jan);
			if (book == null) {
				request.getSession().setAttribute("errorMessage", "書籍の詳細がありません。");
		        response.sendRedirect("error.jsp");
		        return;
			} else {
				request.setAttribute("book", book);
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

		request.getRequestDispatcher("book-edit.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
 
		try {
			BookDAO bDao = new BookDAO();
			
			String jan = request.getParameter("jan");
			String isbn = request.getParameter("isbn");
			String bookName = request.getParameter("bookName");
			String bookKana = request.getParameter("bookKana");
			int price = Integer.parseInt(request.getParameter("price")) ;
			Date issueDate = null;
			LocalDateTime createDate = null;
			LocalDateTime updateDate = null;
			
			if(isbn.length() != 13) {
				request.getSession().setAttribute("isbnError", "ISBNコードは13桁で登録してください。");
				response.sendRedirect("BookEditServlet?jan=" + jan);
				return;
			}
			
			if(price < 0) {
				request.getSession().setAttribute("priceError", "「価格」が設定されていません。");
				response.sendRedirect("BookEditServlet?jan=" + jan);
				return;
			}
			
			if(bookName == null || bookName.isEmpty()) {
				request.getSession().setAttribute("bookNameError", "「書籍名」が設定されていません。");
				response.sendRedirect("BookEditServlet?jan=" + jan);
				return;
			}
			if(bookKana == null || bookKana.isEmpty()) {
				request.getSession().setAttribute("bookKanaError", "「書籍名カナ」が設定されていません。");
				response.sendRedirect("BookEditServlet?jan=" + jan);
				return;
			}

			String issueDateParam = request.getParameter("issueDate");
			if (issueDateParam != null && !issueDateParam.isEmpty()) {
				issueDate = Date.valueOf(issueDateParam);
			}

			String createDateParam =  request.getParameter("createDate");
	        if (createDateParam != null && !createDateParam.isEmpty()) {
	            createDate = LocalDateTime.of(LocalDate.parse(createDateParam), LocalTime.now());
	        }

			String updateDateParam = request.getParameter("updateDate");
			if (updateDateParam != null && !updateDateParam.isEmpty()) {
				updateDate = LocalDateTime.of(LocalDate.parse(updateDateParam), LocalTime.now());
			}

			int row = bDao.editBook(jan, isbn, bookName, bookKana, price, issueDate, createDate, updateDate);
			if (row != 1) {
				request.getSession().setAttribute("editError", "編集に失敗しました。");
				response.sendRedirect("BookEditServlet?jan=" + jan);
				return;
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

}
