package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EditCommentServlet
 */
@WebServlet("/EditCommentServlet")
public class EditCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String comment = request.getParameter("comment");		
		int comment_key = Integer.parseInt(request.getParameter("comment_key")); 
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";
		System.out.println(comment_key);
		System.out.println(comment);
		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
				// 메소드 호출을 통한 데이터베이스 로직 처리
				if (EditComment(connection, comment_key,comment)) {
					// 성공 메시지 출력
					String json = "{\"status\": \"success\"}";
	                response.setContentType("application/json");
	                response.setCharacterEncoding("UTF-8");
	                response.getWriter().write(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String json = "{\"status\": \"error\"}";
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
		}
	}
	private boolean EditComment(Connection connection, int comment_key, String ch_comment) throws SQLException {
		// SQL 쿼리 작성
		String sql = "UPDATE comment SET content = ? WHERE comment_key = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, ch_comment);
			statement.setInt(2, comment_key);

			int rowsAffected = statement.executeUpdate();
			// 성공적으로 행이 삭제되었는지 확인
			return rowsAffected > 0;
		}
	}


}
