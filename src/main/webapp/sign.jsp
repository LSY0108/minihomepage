<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/202244035_final/css/sign.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- <link rel="stylesheet" href="css/sign.css"> -->
</head>
<body>
	<a href='/'>홈</a>
	<div class="bookcover">
		<div class="bookdot">
			<div class="page">
				<div class="signpage">
					<form action="/202244035_final/signServlet" method="post" onsubmit="return validateForm();">
						<div class="title">
							<b><mark>회원가입</mark></b>
						</div>
						<hr>
						<input type="text" id="user_id" name="user_id" class="text-field" placeholder="아이디를 입력하세요."> 
						<input type="password" id="user_pwd" name="user_pwd" class="text-field" placeholder="비밀번호 입력하세요.">
						<input type="text" id="user_name" name="user_name" class="text-field" placeholder="이름 입력하세요."> 
						<input type="text" id="user_nickname" name="user_nickname" class="text-field" placeholder="닉네임을 입력하세요.">
						<input type="submit" name="sign" value="회원가입" class="btn">
						<hr>

						<!--여기에 아이디/비번찾기 링크 삽입-->
					</form>
				</div>
			</div>
		</div>
	</div>
<script>
	 function validateForm() {
	        var user_id = document.getElementById('user_id').value;
	        var user_pwd = document.getElementById('user_pwd').value;
	        var user_name = document.getElementById('user_name').value;
	        var user_nickname = document.getElementById('user_nickname').value;

	        if (user_id === '' || user_pwd === '') {
	            alert('아이디와 비밀번호를 입력하세요.');
	            return false; // 폼 제출 막기
	        }else if(user_name === ''){
	        	alert('이름을 입력하세요.');
	            return false; // 폼 제출 막기
	        }else if(user_nickname === ''){
	        	alert('닉네임을 입력하세요.');
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