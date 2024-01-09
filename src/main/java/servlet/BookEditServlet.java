package servlet;

import java.io.IOException;
import java.sql.Date;
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
				request.setAttribute("noDetail", "詳細がありません。");
			} else {
				request.setAttribute("book", book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("book-edit.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		BookDAO bDao = new BookDAO();

		String jan = request.getParameter("jan");
		String isbn = request.getParameter("isbn");
		String bookName = request.getParameter("bookName");
		String bookKana = request.getParameter("bookKana");
		int price = 0;
		Date issueDate = null;
		LocalDateTime createDate = null;
		LocalDateTime updateDate = null;

		try {
			String priceParam = request.getParameter("price");
			if (priceParam != null && !priceParam.isEmpty()) {
				price = Integer.parseInt(priceParam);
			}

			String issueDateParam = request.getParameter("issueDate");
			if (issueDateParam != null && !issueDateParam.isEmpty()) {
				issueDate = Date.valueOf(issueDateParam);
			}

			String createDateParam = request.getParameter("createDate");
	        if (createDateParam != null && !createDateParam.isEmpty()) {
	            createDate = LocalDateTime.of(LocalDate.parse(createDateParam), LocalTime.now());
	        }

			String updateDateParam = request.getParameter("updateDate");
			if (updateDateParam != null && !updateDateParam.isEmpty()) {
				updateDate = LocalDateTime.of(LocalDate.parse(updateDateParam), LocalTime.now());
			}

			int row = bDao.editBook(jan, isbn, bookName, bookKana, price, issueDate, createDate, updateDate);
			if (row != 1) {
				request.setAttribute("failure", "編集に失敗しました。");
				request.getRequestDispatcher("book-edit.jsp").forward(request, response);
			} else {
				response.sendRedirect("BookListServlet");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
