package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Order_tbl implements Parcelable {
    private int order_id;
    private int user_id;
    private int venues_id;
    private Venues_tbl venuesDetail;
    private int order_number;
    private Timestamp order_date;
    private Timestamp order_time;
    private int order_length;
    private String order_note;
    private String order_state;
    private double total_price;

    public Order_tbl(int order_id, int user_id,int venues_id, Venues_tbl venuesDetail, int order_number, Timestamp order_date,
                     Timestamp order_time, int order_length, String order_note, String order_state, double total_price) {
        super();
        this.order_id = order_id;
        this.user_id = user_id;
        this.venues_id =venues_id;
        this.venuesDetail = venuesDetail;
        this.order_number = order_number;
        this.order_date = order_date;
        this.order_time = order_time;
        this.order_length = order_length;
        this.order_note = order_note;
        this.order_state = order_state;
        this.total_price = total_price;
    }


    public Order_tbl(int order_id, int user_id, int venues_id, int order_number, Timestamp order_date, Timestamp order_time,
                     int order_length, String order_note, String order_state, double total_price) {
        super();
        this.order_id = order_id;
        this.user_id = user_id;
        this.venues_id = venues_id;
        this.order_number = order_number;
        this.order_date = order_date;
        this.order_time = order_time;
        this.order_length = order_length;
        this.order_note = order_note;
        this.order_state = order_state;
        this.total_price = total_price;
    }

    protected Order_tbl(Parcel in) {
        order_id = in.readInt();
        user_id = in.readInt();
        venues_id = in.readInt();
        venuesDetail = in.readParcelable(Venues_tbl.class.getClassLoader());
        order_number = in.readInt();
        order_length = in.readInt();
        order_note = in.readString();
        order_state = in.readString();
        total_price = in.readDouble();
    }

    public static final Creator<Order_tbl> CREATOR = new Creator<Order_tbl>() {
        @Override
        public Order_tbl createFromParcel(Parcel in) {
            return new Order_tbl(in);
        }

        @Override
        public Order_tbl[] newArray(int size) {
            return new Order_tbl[size];
        }
    };

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVenues_id() {
        return venues_id;
    }

    public void setVenues_id(int venues_id) {
        this.venues_id = venues_id;
    }

    public Venues_tbl getVenuesDetail() {
        return venuesDetail;
    }

    public void setVenuesDetail(Venues_tbl venuesDetail) {
        this.venuesDetail = venuesDetail;
    }

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public Timestamp getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Timestamp order_date) {
        this.order_date = order_date;
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

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order_id);
        dest.writeInt(user_id);
        dest.writeInt(venues_id);
        dest.writeParcelable(venuesDetail, flags);
        dest.writeInt(order_number);
        dest.writeInt(order_length);
        dest.writeString(order_note);
        dest.writeString(order_state);
        dest.writeDouble(total_price);
    }
}
