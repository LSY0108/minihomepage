<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/202244035_final/css/uploadPhoto.css">
</head>
<body>
	<div class="bookcover">
		<div class="bookdot">
			<div class="page">
				<div class="column1">
					<div class="row1">방문자 수 : ${sessionScope.visit}</div>
					<!-- <div class="row2">
						<img src="image/bloom11.png" alt="프로필사진" class="image">
						<div class="text1">상태메세지</div>
						<div class="text2">닉네임</div>
					</div> -->
				</div>
				<div class="column2">
					<div class="row3">
						<c:choose>
							<c:when test="${not empty sessionScope.user_id}">
								<!-- 로그인 상태 -->
								<div class="column3">${sessionScope.user_nickname}의미니홈피     <a href="/202244035_final/logoutServlet"> 로그아웃 </a></div>
							</c:when>
							<c:otherwise>
								<!-- <div class="login">
									<a href="login.jsp" class="btn_login">로그인</a> <a
										href="sign.jsp"><i class="btn_login"></i>회원가입</a>
								</div> -->
							</c:otherwise>
						</c:choose>
					</div>
					<div class="row4">
						<div class="updated">
						<form action="/202244035_final/uploadPhotoServlet" method="post"
								onsubmit="return photoboardNull();" enctype="multipart/form-data">
							<div class="cate">
								*사진첩 작성*
								<hr class="line">
								<div class="photo_title">
									사진 제목 : <input type="text" id="photoTitle" name="photoTitle"
											class="text-field1" placeholder="제목을 입력하세요."
											style="width: 450px;">
								</div>
								<div class="photo_text">
									내용  
								</div>
								<textarea class="gallery_content" id="gallery_content"
										placeholder="내용을 입력해주세요" name="gallery_content"
										style="width: 450px; height: 250px; resize: none;"></textarea>
							</div>
							<div class="img">
								<input type="file" name="gallery_img" id="gallery_img" required>
							</div>
							<input type="submit" value="작성 완료" onclick="" class="button1"> 
							</form>
							<hr class="line">
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
						<!-- <div class='menu5'>
							<a href='myfriend.jsp'>내 친구</a>
						</div> -->
						<div class='menu6'>
							<a href='/202244035_final/myHistoryServlet'>내가 쓴 글</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script>
	function photoboardNull() {
		var photoTitle = document.getElementById('photoTitle').value.trim();
		var gallery_content = document.getElementById('gallery_content').value.trim();
		var gallery_content = document.getElementById('gallery_content').value.trim();
		if (photoTitle === '' || gallery_content === '') {
			alert('제목 또는 내용을 입력하세요.');
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