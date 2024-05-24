<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/202244035_final/css/UpdatePhoto.css">
</head>
<body>
	<div class="bookcover">
		<div class="bookdot">
			<div class="page">
				<div class="column1">
					<div class="row1">방문자 수 : ${sessionScope.visit}</div>
					<div class="row2">
						<img src="image/bloom11.png" alt="프로필사진" class="image">
						<div class="text1">상태메세지</div>
						<div class="text2"><!-- 닉네임 --></div>
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
								<!-- <div class="login">
									<a href="login.jsp" class="btn_login">로그인</a> <a
										href="sign.jsp"><i class="btn_login"></i>회원가입</a>
								</div> -->
							</c:otherwise>
						</c:choose>
					</div>
					<div class="row4">
						<div class="updated">
						<form action="/UpdatePhotoServlet" method="post"
								onsubmit="return boardNull();" enctype="multipart/form-data">
							<div class="cate">
								*사진첩 작성*
								<hr class="line">
								<input type="hidden" id = "gallery_key" name="gallery_key" value="<c:out value="${photo.gallery_key}" />">
								<div class="photo_title">
									사진 제목 : ${photo.gallery_key}<input type="text" id="photoTitle" name="photoTitle"
											value="${photo.photoTitle}" class="text-field1" 
											style="width: 450px;">
								</div>
								<div class="photo_text">
									내용  
								</div>
								<textarea class="gallery_content" id="gallery_content"
										name="gallery_content"
										style="width: 450px; height: 250px; resize: none;">${photo.gallery_content}</textarea>
							</div>
							<div class="img">
							<input type="hidden" name="existing_gallery_img" value="${photo.gallery_img}">
										<div class="img-preview">
											<img
												src="${pageContext.request.contextPath}/image/${photo.gallery_img}"
												alt="게시글 이미지" style="max-width: 450px;" >
										</div>
									<div class="img">
										<label for="gallery_img">이미지 변경:</label> <input type="file"
											name="gallery_img" id="gallery_img">
									</div>
								</div>
							<input type="submit" value="작성 완료" onclick=""> 
							</form>
							<form action="/DeletePhotoServlet" method="post">
								<input type="hidden" name="gallery_key" value="${photo.gallery_key}">
								<input type="submit" value="글 삭제하기">
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
		var gallery_key = document.getElementById('gallery_key').value.trim();
		var photoTitle = document.getElementById('photoTitle').value.trim();
		var gallery_content = document.getElementById('gallery_content').value.trim();
		console.log(gallery_key);
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