package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class addCommentServlet
 */
@WebServlet("/addCommentServlet")
public class addCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addCommentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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

		String comment = request.getParameter("comment");
		int board_key = Integer.parseInt(request.getParameter("board_key"));
		String user_id = request.getParameter("user_id");

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

			// 메소드 호출을 통한 데이터베이스 로직 처리
			if (insertIntoComment(connection, user_id, comment, board_key)) {
				String json = "{\"status\": \"success\", \"comments\": \"" + comment + "\", \"user_id\": \"" + user_id + "\"}";
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
			} else {
				throw new SQLException("Creating comment failed, no ID obtained.");
			}
			// 리다이렉션
			response.sendRedirect("/202244035_final/miniboardServlet");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("데이터베이스 연결 중 오류가 발생했습니다.");
		}

	}

	//메소드를 통한 데이터베이스 로직 처리
	private boolean insertIntoComment(Connection connection, String user_id, String comment, int board_key) throws SQLException {
		// SQL 쿼리 작성
		String sql = "INSERT INTO comment (user_id, content, board_key) VALUES (?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user_id);
			statement.setString(2, comment);
			statement.setInt(3, board_key);

			// 쿼리 실행
			int rowsAffected = statement.executeUpdate();

			// 성공적으로 행이 추가되었는지 확인
			return rowsAffected > 0;
		}
	}

}
