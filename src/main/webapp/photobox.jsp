<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/202244035_final/css/photobox.css">
</head>
<body>
	<div class="bookcover">
		<div class="bookdot">
			<div class="page">
				<div class="column1">
					<div class="row1">방문자 수 : ${sessionScope.visit}</div>
					<div class="row2">
						<c:if test="${not empty profile.profile_img}">
								<img
									src="${pageContext.request.contextPath}/image/${profile.profile_img}"
									alt="Profile Image" class="profile_img"
									style="width: 150px; height: 150px; display: block; margin: 0 auto;" />
							</c:if>
							<div class="text1">${profile.message}</div>
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
							<div class="cate">
								사진첩 <a href='uploadPhoto.jsp' id="writeLink">(작성하기)</a>
								<hr class="line">
							</div>
							<c:forEach var="photo" items="${photoList}">
								<div class="gallery_user">
									 ${photo.user_nickname}님 
									<div class="gallery_date">
										${photo.gallery_date}
										<div class="photoTitle">
											${photo.photoTitle}
											<div class="post_img2">
													<img
														src="${pageContext.request.contextPath}/image/${photo.gallery_img}"
														alt="Photo Image" class="photo-image"
														style="width: 400px; height: 250px; display: block; margin: 0 auto; margin-top:13px; margin-bottom: 13px;" />
											</div>
											<div class="gallery_content">${photo.gallery_content}</div>
										</div>
									</div>
									<hr class="line">
								</div>
							</c:forEach>
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
		<script>
		
	<%String message = (String) session.getAttribute("message");
if (message != null && !message.trim().isEmpty()) {
	out.println("alert('" + message + "');");
	session.removeAttribute("message");
}%>
		
	</script>
</body>
</html>