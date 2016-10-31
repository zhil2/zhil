package com.example.administrator.kdc.vo;

public class User_token_tbl {
private int id;
private int user_id;
private Usershow_tbl usershow_tbl;
private String user_token;
public User_token_tbl(int id, int user_id, String user_token) {
	super();
	this.id = id;
	this.user_id = user_id;
	this.user_token = user_token;
}

public User_token_tbl(int id,Usershow_tbl usershow_tbl, String user_token) {
	super();
	this.id = id;
	this.usershow_tbl = usershow_tbl;
	this.user_token = user_token;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public Usershow_tbl getUsershow_tbl() {
	return usershow_tbl;
}
public void setUsershow_tbl(Usershow_tbl usershow_tbl) {
	this.usershow_tbl = usershow_tbl;
}
public String getUser_token() {
	return user_token;
}
public void setUser_token(String user_token) {
	this.user_token = user_token;
}


	
}
