<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/202244035_final/css/Writemyhome.css">
</head>
<body>
	<form action="/202244035_final/WriteMyHomeServlet" method="post"
		onsubmit="return profileNull();" enctype="multipart/form-data">
		<div class="bookcover">
			<div class="bookdot">
				<div class="page">
					<div class="column1">
						<div class="row1">방문자 수 : ${sessionScope.visit}</div>
						<div class="row2">
							<div class="text1"></div>

							<div class="text2"></div>
						</div>
					</div>
					<div class="column2">
						<div class="row3">

							<c:choose>
								<c:when test="${not empty sessionScope.user_id}">
									<!-- 로그인 상태 -->
									<div class="column3">${sessionScope.user_nickname}의미니홈피     <a href="/202244035_final/logoutServlet"> 로그아웃 </a></div>
								</c:when>
								<c:otherwise>
									<div class="login">
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="row4">
							<div class="updated">
								<div class="cate">
									프로필 (수정하기)
									<hr class="line">
								</div>
								<div class="rectangle">
									<div class="name1">
										이름 : <input type="text" id="profile_name" name="profile_name"
											class="text-field1" style="width: 250px;">
									</div>
									<br>
									<div class="age2">
										나이 : <input type="text" id="age" name="age"
											class="text-field1" style="width: 250px;">
									</div>
									<br>
									<div class="school3">
										학교 : <input type="text" id="school" name="school"
											class="text-field1" style="width: 250px;">
									</div>
									<br>
									<div class="job4">
										직업 : <input type="text" id="job" name="job"
											class="text-field1" style="width: 250px;">
									</div>
									<br>
									<div class="hobby5">
										취미 : <input type="text" id="hobby" name="hobby"
											class="text-field1" style="width: 250px;">
									</div>
									<br>
									<div class="mbti6">
										mbti : <input type="text" id="mbti" name="mbti"
											class="text-field1" style="width: 250px;">
									</div>
									<br>

									<div class="message7">
										상태메세지 : <input type="text" id="message" name="message"
											class="text-field1" style="width: 250px;">
									</div>
									<br>

									<div class="profile_img8">
										프로필 사진 : <input type="file" name="profile_img"
											id="profile_img">
									</div>
									<br>
								</div>
								<input type="submit" value="작성 완료" onclick="" class="button1">
							</div>
						</div>
					</div>

					<div class='container3'>
						<div class='box3'>
							<div class='menu1'>
								<a href='/202244035_final/WriteMyHomeServlet'>마이페이지</a>
							</div>
							<div class='menu2'>
								<a href='/202244035_final/miniboardServlet'>게시판</a>
							</div>
							<div class='menu3'>
								<a href='/202244035_final/uploadPhotoServlet'>사진첩</a>
							</div>
							<div class='menu4'>
								<a href='/202244035_final/guestBookServlet'>방명록</a>
							</div>
							<div class='menu6'>
							<a href='/202244035_final/myHistoryServlet'>내가 쓴 글</a>
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script>
		function profileNull() {
			var hobby = document.getElementById('hobby').value
					.trim();
			var school = document.getElementById('school').value
					.trim();
			var job = document.getElementById('job').value
			.trim();
			var mbti = document.getElementById('mbti').value
			.trim();
			var age = document.getElementById('age').value
			.trim();
			var profile_name = document.getElementById('profile_name').value
			.trim();
			var message = document.getElementById('message').value
			.trim();
			if (hobby === '' || school === '' || job === ''|| mbti === ''|| age === ''|| profile_name === ''|| message === '') {
				alert('모두 입력해주세요.)');
				return false; // 폼 제출 막기
			}
			return true;
		}
	<%String message = (String) session.getAttribute("message");
if (message != null && !message.trim().isEmpty()) {
	out.println("alert('" + message + "');");
	session.removeAttribute("message");
}%>
		
	</script>
</body>
</html>