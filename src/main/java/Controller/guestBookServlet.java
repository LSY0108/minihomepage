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

import model.guestDTO;
import model.myhomeProfileDTO;

@WebServlet("/guestBookServlet")
public class guestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public guestBookServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<guestDTO> guestList = getGuestList();
		String user_id = (String) request.getSession().getAttribute("user_id");
		myhomeProfileDTO profile = getProfile(user_id);
		
		request.setAttribute("guestList", guestList);
		request.setAttribute("profile", profile);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("guestBook.jsp");
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
	             PreparedStatement statement = connection.prepareStatement("SELECT * FROM profile WHERE user_id = ?")) {

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
	private List<guestDTO> getGuestList() {
		List<guestDTO> guestList = new ArrayList<>();
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
					PreparedStatement statement = connection.prepareStatement(
							"SELECT gb.*, u.user_nickname FROM GuestBook gb INNER JOIN USER u ON gb.user_id = u.user_id");
					ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {
					guestDTO guest = new guestDTO();
					// GuestBook 테이블의 모든 컬럼 설정
					guest.setG_key(resultSet.getInt("g_key"));
					guest.setUser_id(resultSet.getString("user_id"));
					guest.setG_content(resultSet.getString("g_content"));
					// 데이터베이스에서 날짜 문자열을 가져옵니다.	
					String dateTime = resultSet.getString("g_date");

					// 문자열을 "yyyy-MM-dd HH:mm" 형식으로 잘라냅니다.
					String formattedTime = dateTime.substring(0, 16);

					// 잘라낸 문자열을 board 객체에 설정합니다.
					guest.setG_date(formattedTime);
					guest.setUser_nickname(resultSet.getString("user_nickname"));
					guestList.add(guest);
				}
				System.out.println("Query executed and results processed"); // 쿼리 실행 및 결과 처리 확인
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage()); // 오류 메시지 출력
		}
		return guestList;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		// JDBC 연결 정보
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		String g_content = request.getParameter("g_content");
		String user_id = (String) session.getAttribute("user_id");

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

			// 메소드 호출을 통한 데이터베이스 로직 처리
			if (insertIntoGuestBook(connection, user_id, g_content)) {
				// 성공 메시지 출력
				session.setAttribute("message", "방명록이 성공적으로 등록되었습니다.");
			} else {
				session.setAttribute("message", "게시글 등록 중 오류가 발생했습니다.");
			}
			// 리다이렉션
			response.sendRedirect("/202244035_final/guestBookServlet");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("데이터베이스 연결 중 오류가 발생했습니다.");
		}
	}

	// 메소드를 통한 데이터베이스 로직 처리
	private boolean insertIntoGuestBook(Connection connection, String user_id, String g_content) throws SQLException {
		// SQL 쿼리 작성
		String sql = "INSERT INTO GuestBook (user_id, g_content, g_date) VALUES (?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user_id);
			statement.setString(2, g_content);

			// 현재 날짜와 시간을 가져와서 설정
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate = sdf.format(new Date());
			statement.setString(3, currentDate);

			// 쿼리 실행
			int rowsAffected = statement.executeUpdate();

			// 성공적으로 행이 추가되었는지 확인
			return rowsAffected > 0;
		}
	}
}