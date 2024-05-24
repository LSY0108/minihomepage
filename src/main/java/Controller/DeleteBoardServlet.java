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

@WebServlet("/DeleteBoardServlet")
public class DeleteBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteBoardServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("포스트으으");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		// JDBC 연결 정보
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		String board_key = request.getParameter("board_key");
		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 데이터베이스 연결
			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
				// 메소드 호출을 통한 데이터베이스 로직 처리
				if (deleteFromMiniBoard(connection, Integer.parseInt(board_key))) {
					// 성공 메시지 출력
					session.setAttribute("message", "게시글이 성공적으로 삭제되었습니다.");
				} else {
					session.setAttribute("message", "게시글 삭제 중 오류가 발생했습니다.");
				}
				// 리다이렉션 또는 다른 페이지로 이동
				response.sendRedirect("/202244035_final/myHistoryServlet");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("데이터베이스 연결 중 오류가 발생했습니다.");
		}
	}

	// 게시글 삭제 로직
	private boolean deleteFromMiniBoard(Connection connection, int board_key) throws SQLException {
		// SQL 쿼리 작성
		String sql = "DELETE FROM MiniBoard WHERE board_key = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, board_key);

			int rowsAffected = statement.executeUpdate();
			// 성공적으로 행이 삭제되었는지 확인
			return rowsAffected > 0;
		}
	}

}
