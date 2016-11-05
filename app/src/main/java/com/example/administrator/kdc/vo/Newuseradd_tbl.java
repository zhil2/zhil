package com.example.administrator.kdc.vo;

import java.sql.Timestamp;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Newuseradd_tbl  {
    private int newuseradd_id;
    private Usershow_tbl usershow_tbl;
    private Double newuseradd_j;
    private Double newuseradd_w;
    private int address_id;
    private Timestamp newuseradd_time;
    
    
    
	public Newuseradd_tbl(int newuseradd_id, Usershow_tbl usershow_tbl, Double newuseradd_j, Double newuseradd_w,
			int address_id,Timestamp newuseradd_time) {
		
		super();
		this.newuseradd_id = newuseradd_id;
		this.usershow_tbl = usershow_tbl;
		this.newuseradd_j = newuseradd_j;
		this.newuseradd_w = newuseradd_w;
		this.address_id = address_id;
		this.newuseradd_time = newuseradd_time;
	}
	
	
	public Newuseradd_tbl() {
		// TODO Auto-generated constructor stub
	}


	public Timestamp getNewuseradd_time() {
		return newuseradd_time;
	}


	public void setNewuseradd_time(Timestamp newuseradd_time) {
		this.newuseradd_time = newuseradd_time;
	}


	public int getNewuseradd_id() {
		return newuseradd_id;
	}
	public void setNewuseradd_id(int newuseradd_id) {
		this.newuseradd_id = newuseradd_id;
	}
	public Usershow_tbl getUsershow_tbl() {
		return usershow_tbl;
	}
	public void setUsershow_tbl(Usershow_tbl usershow_tbl) {
		this.usershow_tbl = usershow_tbl;
	}
	public Double getNewuseradd_j() {
		return newuseradd_j;
	}
	public void setNewuseradd_j(Double newuseradd_j) {
		this.newuseradd_j = newuseradd_j;
	}
	public Double getNewuseradd_w() {
		return newuseradd_w;
	}
	public void setNewuseradd_w(Double newuseradd_w) {
		this.newuseradd_w = newuseradd_w;
	}
	public int getAddress_id() {
		return address_id;
	}
	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

    

   
    
}
