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

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.PhotoDTO;
import model.myhomeProfileDTO;

@WebServlet("/uploadPhotoServlet")
public class uploadPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public uploadPhotoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user_id = (String) request.getSession().getAttribute("user_id");
		List<PhotoDTO> photoList = getPhotoList(user_id);
		myhomeProfileDTO profile = getProfile(user_id);

		request.setAttribute("photoList", photoList);
		request.setAttribute("profile", profile);

		RequestDispatcher dispatcher = request.getRequestDispatcher("photobox.jsp");
		dispatcher.forward(request, response);
	}

	private myhomeProfileDTO getProfile(String user_id) {
		myhomeProfileDTO profile = null;
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
					PreparedStatement statement = connection
							.prepareStatement("SELECT * FROM profile WHERE user_id = ?")) {

				statement.setString(1, user_id);

				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						profile = new myhomeProfileDTO();
						profile.setMessage(resultSet.getString("message"));
						profile.setProfile_img(resultSet.getString("profile_img"));
						// 필요한 다른 profile 속성들도 가져올 수 있습니다.
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
		return profile;
	}

	private List<PhotoDTO> getPhotoList(String user_id) {
		List<PhotoDTO> photoList = new ArrayList<>();
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
					PreparedStatement statement = connection.prepareStatement(
							"SELECT gc.*, u.user_nickname FROM galleryComment gc INNER JOIN USER u ON gc.user_id = u.user_id WHERE gc.user_id = ?")) {

				statement.setString(1, user_id);
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						PhotoDTO photo = new PhotoDTO();
						// galleryComment 테이블의 모든 컬럼 설정
						photo.setGallery_key(resultSet.getInt("gallery_key"));
						photo.setUser_id(resultSet.getString("user_id"));
						photo.setPhotoTitle(resultSet.getString("photoTitle"));
						photo.setGallery_content(resultSet.getString("gallery_content"));
						photo.setGallery_img(resultSet.getString("gallery_img"));
						// 데이터베이스에서 날짜 문자열을 가져옵니다.
						String dateTime = resultSet.getString("gallery_date");

						// 문자열을 "yyyy-MM-dd HH:mm" 형식으로 잘라냅니다.
						String formattedTime = dateTime.substring(0, 16);

						// 잘라낸 문자열을 board 객체에 설정합니다.
						photo.setGallery_date(formattedTime);
						photo.setUser_nickname(resultSet.getString("user_nickname"));
						photoList.add(photo);
					}
					System.out.println("Query executed and results processed"); // 쿼리 실행 및 결과 처리 확인
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage()); // 오류 메시지 출력
		}
		return photoList;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("miniboardSeravlet");
		HttpSession session = request.getSession();

		// 서블릿 컨텍스트를 통해 실제 파일 시스템의 절대 경로를 얻습니다.
		String appPath = request.getServletContext().getRealPath("");

		// 파일 업로드 설정
		String saveDirectory = appPath + File.separator + "image";
		int maxPostSize = 10 * 1024 * 1024; // 최대 10MB

		// MultipartRequest 인스턴스 생성
		MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8",
				new DefaultFileRenamePolicy());

		// JDBC 연결 정보
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		String gallery_img = multi.getFilesystemName("gallery_img"); // input 태그의 name 속성 값

		String photoTitle = multi.getParameter("photoTitle");
		String gallery_content = multi.getParameter("gallery_content");
		String user_id = (String) session.getAttribute("user_id");

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

			// 메소드 호출을 통한 데이터베이스 로직 처리
			if (insertIntoPhotoBox(connection, user_id, photoTitle, gallery_content, gallery_img)) {
				// 성공 메시지 출력
				session.setAttribute("message", "게시글이 성공적으로 등록되었습니다.");
			} else {
				session.setAttribute("message", "게시글 등록 중 오류가 발생했습니다.");
			}
			// 리다이렉션
			response.sendRedirect("/202244035_final/uploadPhotoServlet");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("데이터베이스 연결 중 오류가 발생했습니다.");
		}
	}

	// 메소드를 통한 데이터베이스 로직 처리
	private boolean insertIntoPhotoBox(Connection connection, String user_id, String photoTitle, String gallery_content,
			String gallery_img) throws SQLException {
		// SQL 쿼리 작성
		String sql = "INSERT INTO galleryComment (user_id, photoTitle, gallery_content, gallery_date, gallery_img) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user_id);
			statement.setString(2, photoTitle);
			statement.setString(3, gallery_content);

			// 현재 날짜와 시간을 가져와서 설정
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate = sdf.format(new Date());
			statement.setString(4, currentDate);
			statement.setString(5, gallery_img);

			// 쿼리 실행
			int rowsAffected = statement.executeUpdate();

			// 성공적으로 행이 추가되었는지 확인
			return rowsAffected > 0;
		}
	}
}
