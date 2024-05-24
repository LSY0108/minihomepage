package model;

public class guestDTO {
	private int g_key;
	private String user_id;
	private String g_content;
	private String g_date;
	private String user_nickname;
	
	public int getG_key() {
		return g_key;
	}
	public void setG_key(int g_key) {
		this.g_key = g_key;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getG_content() {
		return g_content;
	}
	public void setG_content(String g_content) {
		this.g_content = g_content;
	}
	public String getG_date() {
		return g_date;
	}
	public void setG_date(String g_date) {
		this.g_date = g_date;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	
}
