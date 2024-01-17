<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>エラー</title>
</head>
<body>
<% String errorMessage = (String)request.getSession().getAttribute("errorMessage"); %>
	<% if(errorMessage != null) { %>
		<p><%=errorMessage %></p>
	<% session.removeAttribute("errorMessage"); %>
<% } %>
<a href="BookListServlet">一覧へ戻る</a>
</body>
</html>