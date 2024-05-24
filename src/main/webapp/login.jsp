<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/202244035_final/css/login.css">
</head>
<body>
	<a href='index.jsp'>홈</a>
	<div class="bookcover">
		<div class="bookdot">
			<div class="page">
				<div class="loginpage">
					<form action="/202244035_final/loginServlet" method="post"
						onsubmit="return validateForm();">
						<div class="title">
							<b><mark>로그인</mark></b>
						</div>
						<hr>
						<input type="text" id="user_id" name="user_id" class="text-field"
							placeholder="아이디를 입력하세요."> <input type="password"
							id="user_pwd" name="user_pwd" class="text-field"
							placeholder="비밀번호 입력하세요."> <input type="submit"
							name="login" value="로그인" class="btn"> <input
							type="button" value="회원가입" onclick="location.href='sign.jsp'">
						<hr>
						<div class="findpwd">
							<a href="#"><b><mark>비밀번호 찾기</mark></b></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		function validateForm() {
			var user_id = document.getElementById('user_id').value;
			var user_pwd = document.getElementById('user_pwd').value;

			if (user_id === '' || user_pwd === '') {
				alert('아이디와 비밀번호를 입력하세요.');
				return false; // 폼 제출 막기
			}
			return true;
		}
		
		<%
	    String message = (String)session.getAttribute("message");
	    if (message != null && !message.trim().isEmpty()) {
	        out.println("alert('" + message + "');");
	        session.removeAttribute("message");
	    }
	%>
	</script>
</body>
</html>