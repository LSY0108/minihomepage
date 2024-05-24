<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/202244035_final/css/board.css">
</head>
<body>
	<div class="bookcover">
		<div class="bookdot">
			<div class="page">
				<div class="column1">
					<div class="row1">방문자 ddddd수 : ${sessionScope.visit}</div>
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
								게시판 <a href='writerBoard.jsp' id="writeLink">(작성하기)</a>
								<hr class="line">
							</div>
							<c:forEach var="board" items="${boardList}">
								<input type="hidden" id="board_key" value="${board.board_key}">
	    						<input type="hidden" id="loggedInEmail" value="${sessionScope.user_id}">
								<div class="board_user">
									${board.user_nickname}님
									<div class="board_date">
										${board.board_date}
										<div class="board_title">
											${board.board_title}
											<div class="post_img2">
												<c:if test="${not empty board.board_img}">
													<img
														src="${pageContext.request.contextPath}/image/${board.board_img}"
														alt="Board Image" class="board-image"
														style="width: 400px; height: 250px; display: block; margin: 0 auto; margin-bottom: 13px;" />
												</c:if>
											</div>
											<div class="board_content">${board.board_content}</div>
										</div>
									</div>
									
									댓글
									<div class="comment-section">
									    <div class="comment-form">
									        <textarea class="comment-input" id="comment-input-${board.board_key}" placeholder="댓글을 입력하세요..."></textarea>
									        <button class="comment-button" data-board-key="${board.board_key}" onclick="addcomment(this);">댓글 달기</button>
									    </div>
									    <div class="comment-list" id="comment-list">
									    	<c:forEach var="comment" items="${comments}">
										    	<c:if test="${board.board_key == comment.board_key}">
									            	<div class="comment" id="comment_${comment.comment_key}">>
									                   <span id="commentText_${comment.comment_key}">${comment.content}</span> - ${comment.user_id}
	        											<a href="#" class="button editCommentButton" data-comment-id="${comment.comment_key}"  data-comment-email="${comment.user_id}">수정</a>
	                								   <a href="#" class="button deleteCommentButton" data-comment-id="${comment.comment_key}">삭제</a>
									            	</div>
								            	</c:if>
								            </c:forEach>
									    </div>
									</div>
									<br>
								<hr class="line" style="margin-top=20px;">
								
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
						<!-- <div class='menu5'>
							<a href='myfriend.jsp' id="myfriend_id">내 친구</a>
						</div> -->
						<div class='menu6'>
							<a href='/202244035_final/myHistoryServlet'>내가 쓴 글</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script> //댓글 추가
	function addcomment(buttonElement){
	    var board_key = buttonElement.getAttribute('data-board-key');
	    var commentInputId = 'comment-input-' + board_key;
	    var comment = $('#' + commentInputId).val();
	    var user_id = '${sessionScope.user_id}';
            if(comment === null){
            	alert('내용을 적어주세여');
            }
            console.log(board_key);
            console.log(comment);
            $.ajax({
                type: "POST",
                url: "/202244035_final/addCommentServlet",
                data: {
                    board_key: board_key,
                    comment: comment,
                    user_id : user_id
                },
                success: function(response) {
                    alert('댓글 작성 완료');
                    window.location.reload();
                },
                error: function(xhr, status, error) {
                    // 오류 처리
                    alert("댓글 추가에 실패했습니다.", error);
                }
            });
        }
	</script>
	
	<script> //댓글 삭제
		document.querySelectorAll('.deleteCommentButton').forEach(function(button) {
		    button.addEventListener('click', function(event) {
		        event.preventDefault();
		        var commentId = this.getAttribute('data-comment-id');
		        console.log(commentId);
		        if (confirm('정말 삭제하시겠습니까?')) {
		        	$.ajax({
			            type: "POST",
		            	url: "/202244035_final/DelCommentServlet",
		            	data: {
			                comment_key : commentId
		            	},
		            	success: function(response) {
			            	console.log(response);
		                	alert('댓글 삭제 완료');
		                	window.location.reload();
		            	},
		            	error: function(xhr, status, error) {
			                alert("댓글 삭제에 실패했습니다.", error);
		            	}
		        	});
		        }
		    });
		});
	</script>
	
	<script> //댓글수정
		document.addEventListener('DOMContentLoaded', function () {
		    document.querySelectorAll('.editCommentButton').forEach(function(button) {
		        button.addEventListener('click', function(event) {
		            // 기본 동작 방지
		            event.preventDefault();

		            // 로그인된 이메일과 댓글 작성자 이메일 비교
		            var loggedInEmail = '${sessionScope.user_id}';
		            var commentEmail = this.getAttribute('data-comment-email');
		            if (loggedInEmail !== commentEmail) {
		                alert('본인의 댓글만 수정이 가능합니다.');
		                return;
		            }

		            var commentId = this.getAttribute('data-comment-id');
		            var commentDiv = document.getElementById('comment_' + commentId);
		            var commentTextDiv = document.getElementById('commentText_' + commentId);

		            // 수정 및 삭제 버튼 숨기기
		            var editButton = commentDiv.querySelector('.editCommentButton');
		            var deleteButton = commentDiv.querySelector('.deleteCommentButton');
		            editButton.style.display = 'none';
		            deleteButton.style.display = 'none';

		            var currentCommentText = commentTextDiv.textContent;

		            // 입력창과 저장 버튼 생성
		            var editInput = document.createElement('textarea');
		            editInput.value = currentCommentText;
		            var saveButton = document.createElement('button');
		            saveButton.textContent = '저장';

		            // 저장 버튼 클릭 이벤트 처리
		            saveButton.addEventListener('click', function() {
		                var updatedComment = editInput.value;

		                // AJAX 요청으로 댓글 수정
		                $.ajax({
		                    type: "POST",
		                    url: "/202244035_final/EditCommentServlet",
		                    data: {
		                        comment_key: commentId,
		                        comment: updatedComment,
		                        user_id : loggedInEmail
		                    },
		                    success: function(response) {
		                        alert('수정 완료');
		                        commentTextDiv.textContent = updatedComment;
		                        // 수정 및 삭제 버튼 다시 보이기
		                        editButton.style.display = '';
		                        deleteButton.style.display = '';
		                    },
		                    error: function(xhr, status, error) {
		                        alert("댓글 수정에 실패했습니다.");
		                        editButton.style.display = '';
		                        deleteButton.style.display = '';
		                    }
		                });
		            });

		            // 현재 댓글 내용을 입력창과 저장 버튼으로 대체
		            commentTextDiv.innerHTML = '';
		            commentTextDiv.appendChild(editInput);
		            commentTextDiv.appendChild(saveButton);
		        });
		    });
		});

		</script>
	
	<script>
		
			<%String message = (String) session.getAttribute("message");
		if (message != null && !message.trim().isEmpty()) {
			out.println("alert('" + message + "');");
			session.removeAttribute("message");
		}%>
		
	</script>
</body>
</html>