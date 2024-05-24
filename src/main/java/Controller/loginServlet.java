package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public loginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("GET 요청은 지원되지 않습니다.");
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

		String userId = request.getParameter("user_id");
		String userPwd = request.getParameter("user_pwd");


		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
			String userNickname = authenticateUser(session, connection, userId, userPwd);
			if (userNickname != null) {
				session.setAttribute("user_id", userId);
				session.setAttribute("user_nickname", userNickname);
				session.setAttribute("message", "로그인 성공");
				response.sendRedirect("/202244035_final/WriteMyHomeServlet");
			} else {
				 session.setAttribute("message", "아이디 또는 비밀번호가 잘못되었습니다.");
				 response.sendRedirect("login.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("데이터베이스 연결 중 오류가 발생했습니다.");
		}
	}

	// 아이디, 비번 인증 메소드
	private String authenticateUser(HttpSession session, Connection connection, String userId, String userPwd)
			throws SQLException {
		String sql = "SELECT user_pwd, user_nickname FROM USER WHERE user_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, userId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String storedPwd = resultSet.getString("user_pwd");
					String storedNickname = resultSet.getString("user_nickname");
					if (storedPwd.equals(userPwd)) {
						session.setAttribute("user_nickname", storedNickname);
						return storedNickname; // 비밀번호 일치 시 닉네임 반환
					}
				}
			}
		}
		return null;
	}
}
