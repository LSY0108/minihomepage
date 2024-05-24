<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/202244035_final/css/myhome.css">
</head>
<body>
	<div class="bookcover">
		<div class="bookdot">
			<div class="page">
				<div class="column1">
					<div class="row1">방문자 수 :${sessionScope.visit}</div>
					<div class="row2">
					
						<c:if test="${not empty profile}">
							<c:set var="profile" value="${profile}" />
							<c:if test="${not empty profile.profile_img}">
								<img
									src="${pageContext.request.contextPath}/image/${profile.profile_img}"
									alt="Profile Image" class="profile_img"
									style="width: 150px; height: 150px; display: block; margin: 0 auto;" />
							</c:if>
							<div class="text1">${profile.message}</div>

							<div class="text2"><%-- ${sessionScope.user_nickname} --%></div>
						</c:if>
					</div>
				</div>
				<div class="column2">
					<div class="row3">

						<c:choose>
							<c:when test="${not empty sessionScope.user_id}">
								<!-- 로그인 상태 -->
								<div class="column3">${sessionScope.user_nickname}의미니홈피       <a href="/202244035_final/logoutServlet"> 로그아웃 </a></div>
								
							</c:when>
							<c:otherwise>
								<div class="login">
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="row4">
						<div class="updated">
							<div class="cate_profile">
								프로필 <a href='Writemyhome.jsp' id="writeLink"> (수정하기)</a>
								<hr class="line">
							</div>
							<div class="rectangle">
								<div class="name" style="font-family: NeoDunggeunmo;">이름 : ${profile.profile_name}</div>
								<br>
								<div class="age" style="font-family: NeoDunggeunmo;">나이 : ${profile.age}</div>
								<br>
								<div class="school" style="font-family: NeoDunggeunmo;">학교 : ${profile.school}</div>
								<br>
								<div class="job" style="font-family: NeoDunggeunmo;">직업 : ${profile.job}</div>
								<br>
								<div class="hobby" style="font-family: NeoDunggeunmo;">취미 : ${profile.hobby}</div>
								<br>
								<div class="mbti" style="font-family: NeoDunggeunmo;">mbti : ${profile.mbti}</div>
								<br>
							</div>
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
							<a href='myfriend.jsp' id='myfriend_id'>내 친구</a>
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
			<%String message = (String) session.getAttribute("message");
				if (message != null && !message.trim().isEmpty()) {
					out.println("alert('" + message + "');");
					session.removeAttribute("message");
			}%>
	</script>
</body>
</html>