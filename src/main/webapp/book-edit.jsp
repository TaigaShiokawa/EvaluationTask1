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
<link rel="stylesheet" href="css/style.css">
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
	    
	    <div class="content-container">
	    <button type="submit">変更する</button>
		<button id="deleteButton">削除する</button><br>
		</div>
	</form>
	
	<div class="back">
	<a href="BookListServlet">一覧に戻る</a>
	</div>
	
	<% String isbnError = (String)request.getSession().getAttribute("isbnError"); %>
	<% String priceError = (String)request.getSession().getAttribute("priceError"); %>
	<% String bookNameError = (String)request.getSession().getAttribute("bookNameError"); %>
	<% String bookKanaError = (String)request.getSession().getAttribute("bookKanaError"); %>
	<% if(isbnError != null) { %>
	<p><%=isbnError %></p>
	<% session.removeAttribute("isbnError"); %>
	<% } else if(priceError != null) { %>
	<p><%=priceError %></p>
	<% session.removeAttribute("priceError"); %>
	<% } else if(bookNameError != null) { %>
	<p><%=bookNameError %></p>
	<% session.removeAttribute("bookNameError"); %>
	<% } else if(bookKanaError != null) { %>
	<p><%=bookKanaError %></p>
	<% session.removeAttribute("bookKanaError"); %>
	<% } %>
<% } %>

	<!-- モーダル -->
	<div id="myModal" class="modal">
	    <!-- モーダルコンテンツ -->
	    <div class="modal-content">
	        <span class="close">&times;</span>
	        <p>本当に削除しますか？</p>
	        <form action="BookDeleteServlet" method="post">
				<input type="hidden" name="jan" value="<%=book.getJAN_CODE() %>">
				<button type="button" id="cancelButton">キャンセル</button>
				<button type="submit">削除する</button>
	        </form>
	    </div>
	</div>
	
<script>
//キャンセルボタン
document.getElementById('cancelButton').addEventListener('click', function() {
    document.getElementById('myModal').style.display = 'none';
});

// 削除ボタン
document.getElementById('deleteButton').addEventListener('click', function(event) {
    event.preventDefault();  // デフォルトのフォーム送信を防止
    document.getElementById('myModal').style.display = 'block';
});


// 閉じるボタン（x）
var span = document.getElementsByClassName("close")[0];
span.onclick = function() {
    document.getElementById('myModal').style.display = "none";
}

// モーダルの外側をクリックした時にモーダルを閉じる
window.onclick = function(event) {
    var modal = document.getElementById("myModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>
</body>
</html>