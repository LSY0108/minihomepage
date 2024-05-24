<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/202244035_final/css/writerBoard.css">
</head>
<body>
	<div class="bookcover">
		<div class="bookdot">
			<div class="page">
				<div class="column1">
					<div class="row1">방문자 수 : ${sessionScope.visit}</div>
					<%-- <div class="row2">
						<c:if test="${not empty profile.profile_img}">
								<img
									src="${pageContext.request.contextPath}/image/${profile.profile_img}"
									alt="Profile Image" class="profile_img"
									style="width: 150px; height: 150px; display: block; margin: 0 auto;" />
							</c:if>
							<div class="text1">${profile.message}</div>
						<div class="text2">닉네임</div>
					</div> --%>
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
							<form action="/202244035_final/miniboardServlet" method="post" onsubmit="return boardNull();" enctype="multipart/form-data">
								<div class="cate">
									*게시글 작성*
									<hr class="line">
									<div class="board_title">
										글 제목 : <input type="text" id="board_title" name="board_title"
											class="text-field1" placeholder="제목을 입력하세요."
											style="width: 450px;">
									</div>
									<div class="board_text1">내용</div>
									<textarea class="board_text" id="board_content"
										placeholder="내용을 입력해주세요" name="board_content"
										style="width: 450px; height: 250px; resize: none;"></textarea>
								</div>
								<div class="img">
									<input type="file" name="board_img" id="board_img">
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
</body>
<script>
	function boardNull() {
		var board_title = document.getElementById('board_title').value.trim();
		var board_content = document.getElementById('board_content').value.trim();
		if (board_title === '' || board_content === '') {
			alert('게시글 제목 또는 내용을 입력하세요.');
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
</html>