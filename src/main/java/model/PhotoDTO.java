package model;

public class PhotoDTO {
	private int gallery_key;
	private String user_id;
	private String gallery_content;
	private String gallery_img;
	private String gallery_date;
	private String photoTitle;
	private String user_nickname;
	
	public int getGallery_key() {
		return gallery_key;
	}
	public void setGallery_key(int gallery_key) {
	    this.gallery_key = gallery_key;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getGallery_content() {
		return gallery_content;
	}
	public void setGallery_content(String gallery_content) {
		this.gallery_content = gallery_content;
	}
	public String getGallery_img() {
		return gallery_img;
	}
	public void setGallery_img(String gallery_img) {
		this.gallery_img = gallery_img;
	}
	public String getGallery_date() {
		return gallery_date;
	}
	public void setGallery_date(String gallery_date) {
		this.gallery_date = gallery_date;
	}
	public String getPhotoTitle() {
		return photoTitle;
	}
	public void setPhotoTitle(String photoTitle) {
		this.photoTitle = photoTitle;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	
}
