package com.example.administrator.kdc.vo;

import java.sql.Timestamp;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Post_reply_tbl {
	private int post_reply_id;
	private int reply_user_id;
	private Usershow_tbl usershow_tbl;
	private String post_reply_text;
	private int reply_user2_id;
	private Timestamp post_reply_date;
	private int post_comment_id;
	private int post_reply_type;//1表示对评论进行回复，2表示对回复进行回复
	public Post_reply_tbl(){

	}

	public Post_reply_tbl(int reply_user_id, Usershow_tbl usershow_tbl, String post_reply_text, int reply_user2_id, Timestamp post_reply_date, int post_comment_id, int post_reply_type) {
		this.reply_user_id = reply_user_id;
		this.usershow_tbl = usershow_tbl;
		this.post_reply_text = post_reply_text;
		this.reply_user2_id = reply_user2_id;
		this.post_reply_date = post_reply_date;
		this.post_comment_id = post_comment_id;
		this.post_reply_type = post_reply_type;
	}

	public Post_reply_tbl(int post_reply_id, int reply_user_id, Usershow_tbl usershow_tbl, String post_reply_text, int reply_user2_id, Timestamp post_reply_date, int post_comment_id, int post_reply_type) {
		this.post_reply_id = post_reply_id;
		this.reply_user_id = reply_user_id;
		this.usershow_tbl = usershow_tbl;
		this.post_reply_text = post_reply_text;
		this.reply_user2_id = reply_user2_id;
		this.post_reply_date = post_reply_date;
		this.post_comment_id = post_comment_id;
		this.post_reply_type = post_reply_type;
	}

	public int getPost_reply_type() {
		return post_reply_type;
	}

	public void setPost_reply_type(int post_reply_type) {
		this.post_reply_type = post_reply_type;
	}

	public int getPost_reply_id() {
		return post_reply_id;
	}

	public void setPost_reply_id(int post_reply_id) {
		this.post_reply_id = post_reply_id;
	}

	public int getReply_user_id() {
		return reply_user_id;
	}

	public void setReply_user_id(int reply_user_id) {
		this.reply_user_id = reply_user_id;
	}

	public Usershow_tbl getUsershow_tbl() {
		return usershow_tbl;
	}

	public void setUsershow_tbl(Usershow_tbl usershow_tbl) {
		this.usershow_tbl = usershow_tbl;
	}

	public String getPost_reply_text() {
		return post_reply_text;
	}

	public void setPost_reply_text(String post_reply_text) {
		this.post_reply_text = post_reply_text;
	}

	public int getReply_user2_id() {
		return reply_user2_id;
	}

	public void setReply_user2_id(int reply_user2_id) {
		this.reply_user2_id = reply_user2_id;
	}

	public Timestamp getPost_reply_date() {
		return post_reply_date;
	}

	public void setPost_reply_date(Timestamp post_reply_date) {
		this.post_reply_date = post_reply_date;
	}

	public int getPost_comment_id() {
		return post_comment_id;
	}

	public void setPost_comment_id(int post_comment_id) {
		this.post_comment_id = post_comment_id;
	}
}
