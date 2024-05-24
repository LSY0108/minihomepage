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

<link rel="stylesheet" href="/202244035_final/css/index.css">
</head>
<body>
	<div class="bookcover">
		<div class="bookdot">
			<div class="page">
				<div class="indexpage">
					<form>
						<div class="title">
							<b><mark>시작하기</mark></b>
						</div>
						<div class="down">
							<b><mark> 로그인이 필요한 서비스입니다.</mark></b>
						</div>
						<hr>
						<div class="btn1">
							<input type="button" value="로그인"
								onclick="location.href='login.jsp'" class="login">
						</div>
						<div class="btn2">
							<input type="button" value="회원가입"
								onclick="location.href='sign.jsp'" class="sign">
						</div>
						<hr>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		// URL 쿼리 스트링에서 메시지를 가져와서 알림으로 표시
	<%String queryMessage = request.getParameter("message");
if (queryMessage != null && !queryMessage.trim().isEmpty()) {
	out.println("alert('" + queryMessage + "');");
}%>
		
	</script>
</body>
</html>