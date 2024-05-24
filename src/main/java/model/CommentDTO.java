package model;

import java.util.Date;

public class CommentDTO {
	int comment_key;
	String user_id;
	String content;
	Date content_date;
	int board_key;
	
	public int getComment_key() {
		return comment_key;
	}
	public void setComment_key(int comment_key) {
		this.comment_key = comment_key;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getContent_date() {
		return content_date;
	}
	public void setContent_date(Date content_date) {
		this.content_date = content_date;
	}
	public int getBoard_key() {
		return board_key;
	}
	public void setBoard_key(int board_key) {
		this.board_key = board_key;
	}
	
	
}
