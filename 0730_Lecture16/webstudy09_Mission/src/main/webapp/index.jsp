<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
response.sendRedirect("login.do");
//out.print(request.getContextPath());
%>
<a href="login.do"> 로그인하러 가기 </a>
</body>
</html>