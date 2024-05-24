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

import model.myhomeProfileDTO;

@WebServlet("/WriteMyHomeServlet")
public class WriteMyHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WriteMyHomeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");

		if(user_id != null && !user_id.isEmpty()) {
		myhomeProfileDTO profile = getProfileList(user_id);
		request.setAttribute("profile", profile);
		session.setAttribute("visit",profile.getVisit());
		RequestDispatcher dispatcher = request.getRequestDispatcher("myhome.jsp");
		dispatcher.forward(request, response);
		}else {
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
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

		String profile_img = multi.getFilesystemName("profile_img"); // input 태그의 name 속성 값
		String profile_name = multi.getParameter("profile_name");
		String age = multi.getParameter("age");
		String school = multi.getParameter("school");
		String job = multi.getParameter("job");
		String hobby = multi.getParameter("hobby");
		String mbti = multi.getParameter("mbti");
		String message = multi.getParameter("message");
		String user_id = (String) session.getAttribute("user_id");

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 데이터베이스 연결
			Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
			
			boolean profileExists = doesProfileExist(connection, user_id);
			
			
			// 메소드 호출을 통한 데이터베이스 로직 처리
			if (profileExists) {
				if(updateProfile(connection, user_id, profile_name, age, school, job, hobby, mbti, message, profile_img)){
			        session.setAttribute("message", "프로필 업데이트 성공.");
			    } else {
			        session.setAttribute("message", "프로필 업데이트 실패.");
			    }
			} 
			else {
				if(insertIntoProfile(connection, user_id, profile_name, age, school, job, hobby, mbti, message, profile_img)){
			        session.setAttribute("message", "프로필 생성 성공.");
			    } else {
			        session.setAttribute("message", "프로필 생성 실패.");
			    }
	        }

			// 리다이렉션
			response.sendRedirect("/202244035_final/WriteMyHomeServlet");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("데이터베이스 연결 중 오류가 발생했습니다.");
		}
	}

	private boolean doesProfileExist(Connection connection, String user_id) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM profile WHERE user_id = ?";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, user_id);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getInt(1) > 0;
	        }
	        return false;
	    }
	}
	
	private myhomeProfileDTO getProfileList(String user_id) {
		myhomeProfileDTO profile = new myhomeProfileDTO();
	    String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
	    String dbUser = "root";
	    String dbPassword = "fbrudfhr14";

	    Connection connection = null; // 연결 객체 초기화

	    try {
	        // JDBC 드라이버 로드
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        // 데이터베이스 연결
	        connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
	        connection.setAutoCommit(false); // 트랜잭션 시작

	        // visit 칼럼 업데이트 쿼리
	        try (PreparedStatement updateVisitStatement = connection.prepareStatement("UPDATE profile SET visit = visit + 1 WHERE user_id = ?")) {
	            updateVisitStatement.setString(1, user_id);
	            updateVisitStatement.executeUpdate();
	        }

	        // 프로필 목록 조회
	        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM profile WHERE user_id = ?")) {
	            statement.setString(1, user_id);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    profile.setUser_id(resultSet.getString("user_id"));
						profile.setHobby(resultSet.getString("hobby"));
						profile.setSchool(resultSet.getString("school"));
						profile.setJob(resultSet.getString("job"));
						profile.setMbti(resultSet.getString("mbti"));
						profile.setAge(resultSet.getString("age"));
						profile.setProfile_name(resultSet.getString("profile_name"));
						profile.setMessage(resultSet.getString("message"));
						profile.setProfile_img(resultSet.getString("profile_img"));
						profile.setVisit(resultSet.getInt("visit"));
	                }
	            }
	        }

	        connection.commit(); // 트랜잭션 커밋
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        if (connection != null) {
	            try {
	                connection.rollback(); // 에러 발생 시 롤백
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    } finally {
	        // 데이터베이스 연결 닫기
	        try {
	            if (connection != null) connection.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	    return profile;
	}
	

	// 메소드를 통한 데이터베이스 로직 처리
	private boolean updateProfile(Connection connection, String user_id, String profile_name, String age, String school,
			String job, String hobby, String mbti, String message, String profile_img) throws SQLException {
		// SQL 쿼리 작성
		String sql = "UPDATE profile SET profile_name = ?, age = ?, school = ?, job = ?, hobby = ?, mbti = ?, message = ?, profile_img = ? WHERE user_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, profile_name);
			statement.setString(2, age);
			statement.setString(3, school);
			statement.setString(4, job);
			statement.setString(5, hobby);
			statement.setString(6, mbti);
			statement.setString(7, message);
			statement.setString(8, profile_img);
			statement.setString(9, user_id);

			// 쿼리 실행
			int rowsAffected = statement.executeUpdate();

			// 성공적으로 행이 추가되었는지 확인
			return rowsAffected > 0;
		}
	}

	private boolean insertIntoProfile(Connection connection, String user_id, String profile_name, String age,
			String school, String job, String hobby, String mbti, String message, String profile_img)
			throws SQLException {
		// SQL 쿼리 작성
		String sql = "INSERT INTO profile (user_id, profile_name, age, school, job, hobby, mbti, message, profile_img) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user_id);
			statement.setString(2, profile_name);
			statement.setString(3, age);
			statement.setString(4, school);
			statement.setString(5, job);
			statement.setString(6, hobby);
			statement.setString(7, mbti);
			statement.setString(8, message);
			statement.setString(9, profile_img);

			// 쿼리 실행
			int rowsAffected = statement.executeUpdate();
			System.out.println("==>" + rowsAffected);
			// 성공적으로 행이 추가되었는지 확인
			return rowsAffected > 0;
		}
	}
}