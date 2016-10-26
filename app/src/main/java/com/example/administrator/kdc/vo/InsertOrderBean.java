package com.example.administrator.kdc.vo;


import java.sql.Timestamp;

public class InsertOrderBean {
    private int user_id;
    private int order_number;
    private Timestamp order_time;
    private int order_length;
    private String order_note;
    private Venues_tbl venuesDetail;
    private double total_price;
	public InsertOrderBean(){

	}

	public InsertOrderBean(int user_id, int order_number, Timestamp order_time, int order_length, String order_note,
			Venues_tbl venuesDetail, double total_price) {
		super();
		this.user_id = user_id;
		this.order_number = order_number;
		this.order_time = order_time;
		this.order_length = order_length;
		this.order_note = order_note;
		this.venuesDetail = venuesDetail;
		this.total_price = total_price;
	}
	public double getTotal_price() {
		return total_price;
	}


	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}


	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getOrder_number() {
		return order_number;
	}
	public void setOrder_number(int order_number) {
		this.order_number = order_number;
	}
	public Timestamp getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Timestamp order_time) {
		this.order_time = order_time;
	}
	public int getOrder_length() {
		return order_length;
	}
	public void setOrder_length(int order_length) {
		this.order_length = order_length;
	}
	public String getOrder_note() {
		return order_note;
	}
	public void setOrder_note(String order_note) {
		this.order_note = order_note;
	}
	public Venues_tbl getVenuesDetail() {
		return venuesDetail;
	}
	public void setVenuesDetail(Venues_tbl venuesDetail) {
		this.venuesDetail = venuesDetail;
	}


}
