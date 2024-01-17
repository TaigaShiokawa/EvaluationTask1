<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="model.dto.*" %>
<%@ page import="model.dao.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.stream.IntStream" %>
<%@ page import="java.time.*" %>
<%@ page import="java.text.NumberFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書籍一覧</title>
<link rel="stylesheet" href="css/style.css">
<script>
//JANコード取得
function goToBookDetail(janCode) {
    window.location.href = 'BookEditServlet?jan=' + janCode;
}
</script>
</head>
	<% String jan = (String)request.getSession().getAttribute("jan"); %>
	<% String isbn = (String)request.getSession().getAttribute("isbn"); %>
	<% String bookName = (String)request.getSession().getAttribute("bookName"); %>
	<% String bookKana = (String)request.getSession().getAttribute("bookKana"); %>
	<% Integer price = (Integer)request.getSession().getAttribute("price"); %>
	<% Date issueDate = (Date)session.getAttribute("issueDate"); %>
	<% LocalDateTime createDate = (LocalDateTime)session.getAttribute("createDate"); %>
	<% HashMap<String, String> registerError = (HashMap<String, String>)request.getSession().getAttribute("registerError"); %>
	
<div class="content-container">
	<form id="bookRegister" action="BookRegisterServlet" method="post">
		<h2>書籍を追加</h2>
		
	    <label>JAN_CODE：</label><br>
	    <textarea rows="1" cols="30" name="jan" placeholder="JANコードを入力してください。" required><%= jan != null ? jan : "" %></textarea><br>
	    <% if(registerError != null) { %>
	    	<% if(registerError.containsKey("janError")) { %>
	    	<p><%=registerError.get("janError") %></p>
	    	<% session.removeAttribute("registerError"); %>
	    	<% } %>
	    <% } %>
	    
	    <label>ISBN_CODE：</label><br>
	    <textarea rows="1" cols="30" name="isbn" placeholder="ISBNコードを入力してください。" required><%= isbn != null ? isbn : "" %></textarea><br>
	    <% if(registerError != null) { %>
	    	<% if(registerError.containsKey("isbnError")) { %>
	    	<p><%=registerError.get("isbnError") %></p>
	    	<% session.removeAttribute("registerError"); %>
	    	<% } %>
	    <% } %>
	    
	    <label>書籍名：</label><br>
	    <textarea rows="3" cols="30" name="bookName" placeholder="書籍名を入力してください。" required><%= bookName != null ? bookName : "" %></textarea><br>
	    <% if(registerError != null) { %>
	    	<% if(registerError.containsKey("bookNameError")) { %>
	    	<p><%=registerError.get("bookNameError") %></p>
	    	<% session.removeAttribute("registerError"); %>
	    	<% } %>
	    <% } %>
	    
	    <label>書籍名カナ：</label><br>
	    <textarea rows="3" cols="30" name="bookKana" placeholder="書籍名のカタカナ表記をを入力してください。" required><%= bookKana != null ? bookKana : "" %></textarea><br>
	    <% if(registerError != null) { %>
	    	<% if(registerError.containsKey("bookKanaError")) { %>
	    	<p><%=registerError.get("bookKanaError") %></p>
	    	<% session.removeAttribute("registerError"); %>
	    	<% } %>
	    <% } %>
	    
	    <label>価格：</label><br>
	    <input type="number" name="price" placeholder="価格" required value="<%= price != null ? price : "" %>">円<br>
	    <% if(registerError != null) { %>
	    	<% if(registerError.containsKey("priceError")) { %>
	    	<p><%=registerError.get("priceError") %></p>
	    	<% session.removeAttribute("registerError"); %>
	    	<% } %>
	    <% } %>
	    
	    <label>発行日：</label><br>
	    <input type="date" name="issueDate" required value="<%= issueDate != null ? issueDate.toString() : "" %>"><br>
	    
	    <label>登録日：</label><br>
	    <input type="date" name="createDate" required value="<%= createDate != null ? createDate.toLocalDate().toString() : "" %>"><br>
	    
	    <button type="submit">登録する</button>
    </form>

<% List<BookDTO> bookList = (List<BookDTO>)request.getAttribute("bookList"); %>
<% String error = (String)request.getAttribute("error"); %>
<% String notFound = (String)request.getAttribute("notFound"); %>
<% if(notFound != null) { %>
<p><%=notFound %></p>
<% } else if(bookList == null || bookList.isEmpty()) { %>
<p><%=error %></p>
<% } else { %>
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
        <% for(BookDTO book : bookList) { %>
        <tr onclick="goToBookDetail('<%=book.getJAN_CODE()%>')">
            <td><%=book.getJAN_CODE() %></td>
            <td><%=book.getISBN_CODE() %></td>
            <td><%=book.getBOOK_NAME() %></td>
            <td><%=book.getBOOK_KANA_NM() %></td>
            <% 
            NumberFormat formatter = NumberFormat.getNumberInstance();
			String formattedPrice = formatter.format(book.getPRICE());
			%>
            <td><%=formattedPrice %>円</td>
            <td><%=book.getISSUE_DATE() %></td>
            <td><%=book.getCREATE_DATETIME() %></td>
            <td><%=book.getUPDATE_DATETIME() %></td>
        </tr>
        <% } %>
    </table>
<% } %>
</div>

<div class="search">
<h3>検索</h3>
	<form class="search-form" action="BookSearchServlet" method="post">
		
		<label>タイトル：</label>
	    <input type="text" name="title" placeholder="タイトルで検索"><br>
	
	    <label>カナ名：</label>
	    <input type="text" name="kana" placeholder="カナ名で検索"><br>
	
	    <label>価格：</label>
	    <input type="text" name="price" placeholder="価格で検索"><br>
	    
		<button type="submit">検索</button>
	 </form>
	 
</div> 

<p class="attention">※書籍情報を編集、削除したい場合は各行をクリック</p>
<a href="BookListServlet">一覧を表示</a>

<body>
</html>