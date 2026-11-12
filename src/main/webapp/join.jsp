<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<h2>회원가입</h2>
	<hr>
	<form action="signup" method="post">
		아이디:<input type="text" name="userid"><br><br>
		비밀번호:<input type="password" name="userpw"><br><br>
		이름:<input type="text" name="username"><br><br>
		<input type="submit" value="회원가입">
	</form>
</body>
</html>