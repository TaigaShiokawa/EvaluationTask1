<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="model.dto.*" %>
<%@ page import="model.dao.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書籍編集</title>
<style>
table {
    width: 100%;
    border-collapse: collapse;
}

table, th, td {
    border: 1px solid black;
}

th, td {
    padding: 8px;
    text-align: left;
}

th {
    background-color: #f2f2f2;
}
</style>
</head>
<body>

<% BookDTO book = (BookDTO)request.getAttribute("book"); %>
<% String noDetail = (String)request.getAttribute("noDetail"); %>
<% if(book == null) { %>
<p><%=noDetail %></p>
<% } else { %>
	<form action="BookEditServlet" method="post">
		<table>
	        <tr>
	            <th>JANコード</th>
	            <th>ISBNコード</th>
	            <th>書籍名</th>
	            <th>書籍名カナ</th>
	            <th>価格</th>
	            <th>発行日</th>
	            <th>登録日時</th>
	            <th>更新日時</th>
	        </tr>
	        <tr>
	            <td><%=book.getJAN_CODE() %></td>
	            <td><input type="text" name="isbn" value="<%=book.getISBN_CODE() %>"></td>
	            <td><input type="text" name="bookName" value="<%=book.getBOOK_NAME() %>"></td>
	            <td><input type="text" name="bookKana" value="<%=book.getBOOK_KANA_NM() %>"></td>
	            <td><input type="number" name="price" value="<%=book.getPRICE() %>">円</td>
	            <td><input type="date" name="issueDate" value="<%=book.getISSUE_DATE() %>"></td>
	            <td><input type="date" name="createDate" value="<%=book.getCREATE_DATETIME() != null ? book.getCREATE_DATETIME().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE) : "" %>"></td>
				<td><input type="date" name="updateDate" value="<%=book.getUPDATE_DATETIME() != null ? book.getUPDATE_DATETIME().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE) : "" %>"></td>

	        </tr>
	    </table>
	    
	    <input type="hidden" name="jan" value="<%=book.getJAN_CODE() %>">
	    
	    <button type="submit">変更する</button>
	</form>
<% } %>

</body>
</html>