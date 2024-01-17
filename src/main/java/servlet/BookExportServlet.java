package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BookDAO;
import model.dto.BookDTO;

@WebServlet("/BookExportServlet")
public class BookExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<BookDTO> bookList;
		try {
			bookList = getBookList();
			String filePath = exportBooksToFile(bookList, getServletContext());
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=\"booklist.csv\"");
			response.setHeader("Content-Length", String.valueOf(new File(filePath).length()));
			
			Files.copy(Paths.get(filePath), response.getOutputStream());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
        

    }

	
    private List<BookDTO> getBookList() throws ClassNotFoundException, SQLException {
    	BookDAO bDao = new BookDAO();
    	return bDao.getAllBooks();
    }

    private String exportBooksToFile(List<BookDTO> bookList, ServletContext context) {
    	 String filePath = context.getRealPath("/") + "WEB-INF/booklist.csv";

        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            StringBuilder sb = new StringBuilder();
            
            sb.append("JANコード,ISBNコード,書籍名,書籍名カナ,価格,発行日,登録日時,更新日時\n");
            
            for (BookDTO book : bookList) {
                sb.append(book.getJAN_CODE()).append(",");
                sb.append(book.getISBN_CODE()).append(",");
                sb.append(book.getBOOK_NAME()).append(",");
                sb.append(book.getBOOK_KANA_NM()).append(",");
                sb.append(book.getPRICE()).append(",");
                sb.append(book.getISSUE_DATE()).append(",");
                sb.append(book.getCREATE_DATETIME()).append(",");
                sb.append(book.getUPDATE_DATETIME()).append("\n");
            }

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.err.println("CSVファイルの生成に失敗しました: " + e.getMessage());
            return null;
        }

        return filePath;
    }

}
