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
import model.boardDTO;
import model.myhomeProfileDTO;

@WebServlet("/myHistoryServlet")
public class myHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public myHistoryServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");

		if (user_id != null && !user_id.isEmpty()) {
			myhomeProfileDTO profile = getProfile(user_id);
			request.setAttribute("profile", profile);
			
			List<boardDTO> userBoards = getUserBoards(user_id);
			List<PhotoDTO> userPhotos = getUserPhotos(user_id);

			request.setAttribute("userBoards", userBoards);
			request.setAttribute("userPhotos", userPhotos);

			RequestDispatcher dispatcher = request.getRequestDispatcher("myhistory.jsp");
			dispatcher.forward(request, response);
		} else {
			// Redirect to login page or handle unauthenticated user
		}
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
	
	private List<PhotoDTO> getUserPhotos(String user_id) {
		List<PhotoDTO> userPhotos = new ArrayList<>();
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
					PreparedStatement statement = connection
							.prepareStatement("SELECT * FROM galleryComment WHERE user_id = ?")) {

				statement.setString(1, user_id);
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						PhotoDTO photo = new PhotoDTO();
						photo.setGallery_key(resultSet.getInt("gallery_key"));
						photo.setUser_id(resultSet.getString("user_id"));
						photo.setPhotoTitle(resultSet.getString("photoTitle"));
						photo.setGallery_content(resultSet.getString("gallery_content"));
						photo.setGallery_img(resultSet.getString("gallery_img"));
						photo.setGallery_date(resultSet.getString("gallery_date"));
						userPhotos.add(photo);
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return userPhotos;
	}

	private List<boardDTO> getUserBoards(String user_id) {
		List<boardDTO> userBoards = new ArrayList<>();
		String jdbcUrl = "jdbc:mysql://43.201.45.183:3306/minihomepage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
		String dbUser = "root";
		String dbPassword = "fbrudfhr14";

		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
					PreparedStatement statement = connection
							.prepareStatement("SELECT * FROM MiniBoard WHERE user_id = ?")) {

				statement.setString(1, user_id);
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						boardDTO board = new boardDTO();
						board.setBoard_key(resultSet.getInt("board_key"));
						board.setUser_id(resultSet.getString("user_id"));
						board.setBoard_title(resultSet.getString("board_title"));
						board.setBoard_content(resultSet.getString("board_content"));
						board.setBoard_img(resultSet.getString("board_img"));
						board.setBoard_date(resultSet.getString("board_date"));
						userBoards.add(board);
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return userBoards;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
