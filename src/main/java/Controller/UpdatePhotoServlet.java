package Controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.PhotoDTO;

@WebServlet("/UpdatePhotoServlet")
public class UpdatePhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdatePhotoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String gallery_key = request.getParameter("gallery_key"); // 게시글 식별자 받기
		if (gallery_key != null && !gallery_key.isEmpty()) {
			// 데이터베이스에서 boardKey를 사용하여 게시글 조회
			PhotoDTO photo = getPhoto(Integer.parseInt(gallery_key));

			// 조회된 게시글 데이터를 request 객체에 속성으로 추가
			request.setAttribute("photo", photo);
			// 디버그 메시지로 게시글 내용 출력

			// 수정 페이지로 포워딩
			RequestDispatcher dispatcher = request.getRequestDispatcher("UpdatePhoto.jsp");
			dispatcher.forward(request, response);
		} else {
			// board_key가 없는 경우의 처리 (리디렉션 또는 에러 메시지 표시)
		}
	}

	private PhotoDTO getPhoto(int gallery_key) {
		PhotoDTO photo = null;
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
					PreparedStatement statement = connection
							.prepareStatement("SELECT * FROM galleryComment WHERE gallery_key = ?")) {

				// 쿼리 매개변수 설정
				statement.setInt(1, gallery_key);

				// 쿼리 실행 및 결과 처리
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						photo = new PhotoDTO();
						photo.setUser_id(resultSet.getString("user_id"));
						photo.setPhotoTitle(resultSet.getString("photoTitle"));
						photo.setGallery_content(resultSet.getString("gallery_content"));
						photo.setGallery_img(resultSet.getString("gallery_img"));
						photo.setGallery_key(resultSet.getInt("gallery_key"));
						// 데이터베이스에서 날짜 문자열을 가져옵니다.	
						String dateTime = resultSet.getString("gallery_date");

						// 문자열을 "yyyy-MM-dd HH:mm" 형식으로 잘라냅니다.
						String formattedTime = dateTime.substring(0, 16);

						// 잘라낸 문자열을 board 객체에 설정합니다.
						photo.setGallery_date(formattedTime);
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			// 오류 처리
		}
		return photo;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); response.setCharacterEncoding("UTF-8"); response.setContentType("text/html; charset=UTF-8");
		String appPath = request.getServletContext().getRealPath("");
		String saveDirectory = appPath + File.separator + "image";
		int maxPostSize = 10 * 1024 * 1024; // 최대 10MB
		// MultipartRequest 인스턴스 생성
		MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", new DefaultFileRenamePolicy());
		// 파일 업로드 설정
		HttpSession session = request.getSession();	

		// JDBC 연결 정보
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		String gallery_img = multi.getFilesystemName("gallery_img"); // 새 이미지 경로
		String existingGalleryImg = multi.getParameter("existing_gallery_img"); // 기존 이미지 경로
		String photoTitle = multi.getParameter("photoTitle");
		String gallery_key = multi.getParameter("gallery_key");
		String gallery_content = multi.getParameter("gallery_content");

		if (gallery_img == null || gallery_img.isEmpty()) {
			gallery_img = existingGalleryImg; // 새 이미지가 없으면 기존 이미지 경로 사용
	    }
		
		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
				// 메소드 호출을 통한 데이터베이스 로직 처리
				if (UpdateIntoGallery(connection, photoTitle, gallery_content, gallery_img, Integer.parseInt(gallery_key))) {
					// 성공 메시지 출력
					session.setAttribute("message", "게시글이 성공적으로 등록되었습니다.");
				} else {
					session.setAttribute("message", "게시글 등록 중 오류가 발생했습니다.");
				}
				// 리다이렉션
				response.sendRedirect("/202244035_final/myHistoryServlet");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("데이터베이스 연결 중 오류가 발생했습니다.");
		}
	}

	// 메소드를 통한 데이터베이스 로직 처리
	private boolean UpdateIntoGallery(Connection connection, String photoTitle, String gallery_content,
			String gallery_img, int gallery_key) throws SQLException {
		// SQL 쿼리 작성
		String sql = "UPDATE galleryComment SET photoTitle = ?, gallery_content = ?, gallery_img = ?, gallery_date = ? WHERE gallery_key = ?";
		System.out.println("SQL 쿼리: " + sql); // 쿼리 확인
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, photoTitle);
			//System.out.println("board_title: " + board_title);
			statement.setString(2, gallery_content);
			//System.out.println("board_content: " + board_content);
			statement.setString(3, gallery_img);
			//System.out.println("board_img: " + board_img);
			// 현재 날짜와 시간을 가져와서 설정
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate = sdf.format(new Date());
			statement.setString(4, currentDate);
			statement.setInt(5, gallery_key);

			int rowsAffected = statement.executeUpdate();
			// 성공적으로 행이 추가되었는지 확인
			return rowsAffected > 0;
		}
	}
}