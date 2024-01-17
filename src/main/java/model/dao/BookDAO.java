package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import model.dto.BookDTO;

public class BookDAO {

	//一覧表示
	public List<BookDTO> getAllBooks() throws ClassNotFoundException, SQLException {
		List<BookDTO> bookList = new ArrayList<>();
		String sql = "SELECT * FROM BOOK";
		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				BookDTO book = new BookDTO();
				book.setJAN_CODE(res.getString("JAN_CD"));
				book.setISBN_CODE(res.getString("ISBN_CD"));
				book.setBOOK_NAME(res.getString("BOOK_NM"));
				book.setBOOK_KANA_NM(res.getString("BOOK_KANA"));
				book.setPRICE(res.getInt("PRICE"));
				book.setISSUE_DATE(res.getDate("ISSUE_DATE"));

				Timestamp createTimestamp = res.getTimestamp("CREATE_DATETIME");
				if (createTimestamp != null) {
					book.setCREATE_DATETIME(createTimestamp.toLocalDateTime());
				}

				Timestamp updateTimestamp = res.getTimestamp("UPDATE_DATETIME");
				if (updateTimestamp != null) {
					book.setUPDATE_DATETIME(updateTimestamp.toLocalDateTime());
				}

				bookList.add(book);
			}
		}
		return bookList;
	}

	//編集する書籍を取得
	public BookDTO getBook(String jan) throws ClassNotFoundException, SQLException {
		BookDTO book = null;
		String sql = "SELECT * FROM book WHERE JAN_CD = ?";
		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, jan);

			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				book = new BookDTO();
				book.setJAN_CODE(res.getString("JAN_CD"));
				book.setISBN_CODE(res.getString("ISBN_CD"));
				book.setBOOK_NAME(res.getString("BOOK_NM"));
				book.setBOOK_KANA_NM(res.getString("BOOK_KANA"));
				book.setPRICE(res.getInt("PRICE"));
				book.setISSUE_DATE(res.getDate("ISSUE_DATE"));

				Timestamp createTimestamp = res.getTimestamp("CREATE_DATETIME");
				if (createTimestamp != null) {
					book.setCREATE_DATETIME(createTimestamp.toLocalDateTime());
				}

				Timestamp updateTimestamp = res.getTimestamp("UPDATE_DATETIME");
				if (updateTimestamp != null) {
					book.setUPDATE_DATETIME(updateTimestamp.toLocalDateTime());
				}
			}
			return book;
		}
	}

	//書籍編集
	public int editBook(String jan, String isbn, String bookName, String bookKana, int price,
			Date issueDate, LocalDateTime createDateTime, LocalDateTime updateDateTime)
			throws ClassNotFoundException, SQLException {
		int row = 0;
		String sql = "UPDATE book SET ISBN_CD = ?, BOOK_NM = ?, BOOK_KANA = ?, PRICE = ?, "
				+ "ISSUE_DATE = ?, CREATE_DATETIME = ?, UPDATE_DATETIME = ? WHERE JAN_CD = ?";
		try (Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, isbn);
			pstmt.setString(2, bookName);
			pstmt.setString(3, bookKana);
			pstmt.setInt(4, price);
			pstmt.setDate(5, issueDate);
			pstmt.setTimestamp(6, createDateTime == null ? null : Timestamp.valueOf(createDateTime));
			pstmt.setTimestamp(7, updateDateTime == null ? null : Timestamp.valueOf(updateDateTime));
			pstmt.setString(8, jan);

			row = pstmt.executeUpdate();
		} 
		return row;
	}
	
	//書籍登録
	public int registerBook(String jan, String isbn, String bookName, String bookKana, int price, 
			Date issueDate, LocalDateTime createDateTime) throws ClassNotFoundException, SQLException {
		int row = 0;
		String sql = "INSERT INTO BOOK (JAN_CD, ISBN_CD, BOOK_NM, BOOK_KANA, PRICE, ISSUE_DATE, CREATE_DATETIME) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection con = DBConnection.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, jan);
			pstmt.setString(2, isbn);
			pstmt.setString(3, bookName);
			pstmt.setString(4, bookKana);
			pstmt.setInt(5, price);
			pstmt.setDate(6, issueDate);
			pstmt.setTimestamp(7, createDateTime == null ? null : Timestamp.valueOf(createDateTime));
			
			row = pstmt.executeUpdate();
		}
		return row;
	}
	
	//書籍削除(物理削除)
	public int deleteBook(String jan) throws ClassNotFoundException, SQLException {
		int row = 0;
		String sql = "DELETE FROM BOOK WHERE JAN_CD = ?";
		try (Connection con = DBConnection.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, jan);
			
			row = pstmt.executeUpdate();
		}
		return row;
	}
	
	//JANコードの存在を確認
	public boolean janCodeExist(String jan) throws ClassNotFoundException, SQLException {
		boolean exist = false;
		String sql = "SELECT * FROM BOOK WHERE JAN_CD = ?";
		try (Connection con = DBConnection.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, jan);
			
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				exist = true;
			}
		}
		return exist;
	}
	
	//書籍検索
	public List<BookDTO> searchForBooks(String title, String kana, Integer price) throws ClassNotFoundException, SQLException {
		List<BookDTO> searchBookList = new ArrayList<>();
		BookDTO book = new BookDTO();
		StringBuilder sql = new StringBuilder("SELECT * FROM BOOK WHERE 1=1");

	    if (title != null && !title.isEmpty()) {
	        sql.append(" AND BOOK_NM LIKE ?");
	    }
	    
	    if (kana != null && !kana.isEmpty()) {
	        sql.append(" AND BOOK_KANA LIKE ?");
	    }
	    
	    if (price != null) {
	        sql.append(" AND PRICE = ?");
	    }

	    try (Connection con = DBConnection.getConnection(); 
	            PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
	        
	        int index = 1;
	        if (title != null && !title.isEmpty()) {
	            pstmt.setString(index++, "%" + title + "%");
	        }
	        if (kana != null && !kana.isEmpty()) {
	            pstmt.setString(index++, "%" + kana + "%");
	        }
	        if (price != null) {
	            pstmt.setInt(index++, price);
	        }
			
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				book = new BookDTO();
				book.setJAN_CODE(res.getString("JAN_CD"));
				book.setISBN_CODE(res.getString("ISBN_CD"));
				book.setBOOK_NAME(res.getString("BOOK_NM"));
				book.setBOOK_KANA_NM(res.getString("BOOK_KANA"));
				book.setPRICE(res.getInt("PRICE"));
				book.setISSUE_DATE(res.getDate("ISSUE_DATE"));

				Timestamp createTimestamp = res.getTimestamp("CREATE_DATETIME");
				if (createTimestamp != null) {
					book.setCREATE_DATETIME(createTimestamp.toLocalDateTime());
				}

				Timestamp updateTimestamp = res.getTimestamp("UPDATE_DATETIME");
				if (updateTimestamp != null) {
					book.setUPDATE_DATETIME(updateTimestamp.toLocalDateTime());
				}
				
				searchBookList.add(book);
			}
		}
		return searchBookList;
	}
}
