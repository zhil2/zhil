package com.example.administrator.kdc.vo;

import java.sql.Timestamp;
import java.util.List;

public class Post_comment_tbl {
	private int post_comment_id;
	List<Post_reply_tbl> post_reply_tbl;
	private int comment_user_id;
	private Usershow_tbl usershow_tbl;
	private String post_comment_text;
	private String post_comment_picture;
	private Timestamp post_comment_date;
	private int post_id;
	public Post_comment_tbl(){

	}

	public Post_comment_tbl(List<Post_reply_tbl> post_reply_tbl, int comment_user_id, Usershow_tbl usershow_tbl, String post_comment_text, String post_comment_picture, Timestamp post_comment_date, int post_id) {
		this.post_reply_tbl = post_reply_tbl;
		this.comment_user_id = comment_user_id;
		this.usershow_tbl = usershow_tbl;
		this.post_comment_text = post_comment_text;
		this.post_comment_picture = post_comment_picture;
		this.post_comment_date = post_comment_date;
		this.post_id = post_id;
	}

	public Post_comment_tbl(int post_comment_id, List<Post_reply_tbl> post_reply_tbl, int comment_user_id, Usershow_tbl usershow_tbl, String post_comment_text, String post_comment_picture, Timestamp post_comment_date, int post_id) {
		this.post_comment_id = post_comment_id;
		this.post_reply_tbl = post_reply_tbl;
		this.comment_user_id = comment_user_id;
		this.usershow_tbl = usershow_tbl;
		this.post_comment_text = post_comment_text;
		this.post_comment_picture = post_comment_picture;
		this.post_comment_date = post_comment_date;
		this.post_id = post_id;
	}

	public int getPost_comment_id() {
		return post_comment_id;
	}

	public void setPost_comment_id(int post_comment_id) {
		this.post_comment_id = post_comment_id;
	}

	public List<Post_reply_tbl> getPost_reply_tbl() {
		return post_reply_tbl;
	}

	public void setPost_reply_tbl(List<Post_reply_tbl> post_reply_tbl) {
		this.post_reply_tbl = post_reply_tbl;
	}

	public int getComment_user_id() {
		return comment_user_id;
	}

	public void setComment_user_id(int comment_user_id) {
		this.comment_user_id = comment_user_id;
	}

	public Usershow_tbl getUsershow_tbl() {
		return usershow_tbl;
	}

	public void setUsershow_tbl(Usershow_tbl usershow_tbl) {
		this.usershow_tbl = usershow_tbl;
	}

	public String getPost_comment_text() {
		return post_comment_text;
	}

	public void setPost_comment_text(String post_comment_text) {
		this.post_comment_text = post_comment_text;
	}

	public String getPost_comment_picture() {
		return post_comment_picture;
	}

	public void setPost_comment_picture(String post_comment_picture) {
		this.post_comment_picture = post_comment_picture;
	}

	public Timestamp getPost_comment_date() {
		return post_comment_date;
	}

	public void setPost_comment_date(Timestamp post_comment_date) {
		this.post_comment_date = post_comment_date;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
}
