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

import model.userDTO;

@WebServlet("/signServlet")
public class signServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public signServlet() {
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
		String userName = request.getParameter("user_name");
		String userNickname = request.getParameter("user_nickname");
		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

			// 아이디 중복 체크
			if (isUserIdDuplicate(session, connection, userId)) {
				session.setAttribute("message", "이미 사용 중인 아이디입니다. 다른 아이디를 입력하세요.");
				response.sendRedirect("sign.jsp");
				connection.close();
				return;
			}

			// SQL 쿼리 작성
			String sql = "INSERT INTO USER (user_id, user_pwd, user_name, user_nickname) VALUES (?, ?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, userId);
				statement.setString(2, userPwd);
				statement.setString(3, userName);
				statement.setString(4, userNickname);

				// 쿼리 실행
				int result = statement.executeUpdate();

				// 처리 결과에 따른 로직 추가
				if (result > 0) {
					session.setAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
					response.sendRedirect("login.jsp");
				} else {
					session.setAttribute("message", "회원가입에 실패했습니다. 다시 시도해주세요.");
					response.sendRedirect("sign.jsp");
				}

				// 연결 종료
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("데이터베이스 연결 중 오류가 발생했습니다.");

		}
	}


	// 아이디 중복 체크 메서드
	private boolean isUserIdDuplicate(HttpSession session, Connection connection, String userId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM USER WHERE user_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, userId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0;
				}
			}
		}
		return false;
	}

}
