package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Sign_tbl implements Parcelable {
    private int sign_id;
    private String sign_date;
    private int user_id;
    private String sign_yearmonth;
    private String sign_nowdate;


    public Sign_tbl(){

    }

    public Sign_tbl(String sign_date, int user_id, String sign_yearmonth, String sign_nowdate) {
        this.sign_date = sign_date;
        this.user_id = user_id;
        this.sign_yearmonth = sign_yearmonth;
        this.sign_nowdate = sign_nowdate;
    }

    public Sign_tbl(int sign_id, String sign_date, int user_id, String sign_yearmonth, String sign_nowdate) {
        this.sign_id = sign_id;
        this.sign_date = sign_date;
        this.user_id = user_id;
        this.sign_yearmonth = sign_yearmonth;
        this.sign_nowdate = sign_nowdate;
    }

    protected Sign_tbl(Parcel in) {
        sign_id = in.readInt();
        user_id = in.readInt();
        sign_yearmonth = in.readString();
        sign_nowdate = in.readString();
    }

    public static final Creator<Sign_tbl> CREATOR = new Creator<Sign_tbl>() {
        @Override
        public Sign_tbl createFromParcel(Parcel in) {
            return new Sign_tbl(in);
        }

        @Override
        public Sign_tbl[] newArray(int size) {
            return new Sign_tbl[size];
        }
    };

    public int getSign_id() {
        return sign_id;
    }

    public void setSign_id(int sign_id) {
        this.sign_id = sign_id;
    }

    public String getSign_date() {
        return sign_date;
    }

    public void setSign_date(String sign_date) {
        this.sign_date = sign_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSign_yearmonth() {
        return sign_yearmonth;
    }

    public void setSign_yearmonth(String sign_yearmonth) {
        this.sign_yearmonth = sign_yearmonth;
    }

    public String getSign_nowdate() {
        return sign_nowdate;
    }

    public void setSign_nowdate(String sign_nowdate) {
        this.sign_nowdate = sign_nowdate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sign_id);
        dest.writeInt(user_id);
        dest.writeString(sign_yearmonth);
        dest.writeString(sign_nowdate);
    }
}
