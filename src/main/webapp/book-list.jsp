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
<title>書籍一覧</title>
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
<script type="text/javascript">
    function goToBookDetail(janCode) {
        window.location.href = 'BookEditServlet?jan=' + janCode;
    }
</script>
</head>
<% List<BookDTO> bookList = (List<BookDTO>)request.getAttribute("bookList"); %>
<% String error = (String)request.getAttribute("error"); %>
<% if(bookList == null || bookList.isEmpty()) { %>
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
            <td><%=book.getPRICE() %>円</td>
            <td><%=book.getISSUE_DATE() %></td>
            <td><%=book.getCREATE_DATETIME() %></td>
            <td><%=book.getUPDATE_DATETIME() %></td>
        </tr>
        <% } %>
    </table>
<% } %>

<body>
</html>