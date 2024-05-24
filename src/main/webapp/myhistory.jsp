<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/202244035_final/css/myhistory.css">
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
								<div class="cate_wirte1">
									내가 쓴 글
									<hr class="line">
									<ul class="nav nav-tabs" role="tablist">
										선택하기
										<li class="tab-item">
										    <a class="tab-link" onclick="showTab('boardTab', event)">게시판</a>
										</li>
										<li class="tab-item">
										    <a class="tab-link" onclick="showTab('photoTab', event)">사진첩</a>
										</li>
									</ul>
								</div>
								<div id="boardTab" class="tabcontent">
									<div class="board_1">
										게시판
										<hr class="line">
										<c:forEach var="board" items="${userBoards}">
											<div class="column6">
												<div class="row5">
													<a href="UpdateBoardServlet?board_key=${board.board_key}"
														class="blue">수정하기</a><br> ${board.board_title}
													<c:if test="${not empty board.board_img}">
														<img
															src="${pageContext.request.contextPath}/image/${board.board_img}"
															alt="Board Image" class="board-image"
															style="width: 400px; height: 250px; display: block; margin: 0 auto; margin-bottom: 13px;" />
													</c:if>
													${board.board_content} <br><br><br>
												</div>
											</div>
										</c:forEach>
									</div>
								</div>

								<div id="photoTab" class="tabcontent" style="display: none;">
									<div class="board2">
										<div class="cate_wirte2">
											사진첩
											<hr class="line">
										</div>
										<c:forEach var="photo" items="${userPhotos}">
											<div class="column6">
												<div class="row5">
													<a href="UpdatePhotoServlet?gallery_key=${photo.gallery_key}"
														class="red">수정하기</a><br> ${photo.photoTitle}
													<img
														src="${pageContext.request.contextPath}/image/${photo.gallery_img}"
														alt="Photo Image" class="photo-image"
														style="width: 400px; height: 250px; display: block; margin: 0 auto; margin-bottom: 13px;" />
													${photo.gallery_content}<br><br><br>
												</div>
											</div>
										</c:forEach>
									</div>
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
		function showTab(tabName) {
		    var i;
		    var x = document.getElementsByClassName("tabcontent");
		    var tabs = document.getElementsByClassName("tab-link");
		    for (i = 0; i < x.length; i++) {
		        x[i].style.display = "none";  // 모든 탭 컨텐츠 숨김
		        tabs[i].classList.remove("active-tab"); // 모든 탭 링크에서 active-tab 클래스 제거
		    }
		    document.getElementById(tabName).style.display = "block"; // 선택된 탭 컨텐츠 표시
		    event.currentTarget.classList.add("active-tab"); // 현재 클릭된 탭 링크에 active-tab 클래스 추가
		}
	</script>
</body>
</html>