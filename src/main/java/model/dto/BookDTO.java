package model.dto;

import java.sql.Date;
import java.time.LocalDateTime;

public class BookDTO {
	
	private String JAN_CODE;
	private String ISBN_CODE;
	private String BOOK_NAME;
	private String BOOK_KANA_NM;
	private int PRICE;
	private Date ISSUE_DATE;
	private LocalDateTime CREATE_DATETIME;
	private LocalDateTime UPDATE_DATETIME;
	
	public BookDTO() {
		super();
	}
	
	public BookDTO(String jAN_CODE, String iSBN_CODE, String bOOK_NAME, String bOOK_KANA_NM, int pRICE, Date iSSUE_DATE,
			LocalDateTime cREATE_DATETIME, LocalDateTime uPDATE_DATETIME) {
		super();
		JAN_CODE = jAN_CODE;
		ISBN_CODE = iSBN_CODE;
		BOOK_NAME = bOOK_NAME;
		BOOK_KANA_NM = bOOK_KANA_NM;
		PRICE = pRICE;
		ISSUE_DATE = iSSUE_DATE;
		CREATE_DATETIME = cREATE_DATETIME;
		UPDATE_DATETIME = uPDATE_DATETIME;
	}

	public String getJAN_CODE() {
		return JAN_CODE;
	}

	public void setJAN_CODE(String jAN_CODE) {
		JAN_CODE = jAN_CODE;
	}

	public String getISBN_CODE() {
		return ISBN_CODE;
	}

	public void setISBN_CODE(String iSBN_CODE) {
		ISBN_CODE = iSBN_CODE;
	}

	public String getBOOK_NAME() {
		return BOOK_NAME;
	}

	public void setBOOK_NAME(String bOOK_NAME) {
		BOOK_NAME = bOOK_NAME;
	}

	public String getBOOK_KANA_NM() {
		return BOOK_KANA_NM;
	}

	public void setBOOK_KANA_NM(String bOOK_KANA_NM) {
		BOOK_KANA_NM = bOOK_KANA_NM;
	}

	public int getPRICE() {
		return PRICE;
	}

	public void setPRICE(int pRICE) {
		PRICE = pRICE;
	}

	public Date getISSUE_DATE() {
		return ISSUE_DATE;
	}

	public void setISSUE_DATE(Date iSSUE_DATE) {
		ISSUE_DATE = iSSUE_DATE;
	}

	public LocalDateTime getCREATE_DATETIME() {
		return CREATE_DATETIME;
	}

	public void setCREATE_DATETIME(LocalDateTime cREATE_DATETIME) {
		CREATE_DATETIME = cREATE_DATETIME;
	}

	public LocalDateTime getUPDATE_DATETIME() {
		return UPDATE_DATETIME;
	}

	public void setUPDATE_DATETIME(LocalDateTime uPDATE_DATETIME) {
		UPDATE_DATETIME = uPDATE_DATETIME;
	}

}
