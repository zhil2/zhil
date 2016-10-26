package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Muster_tbl implements Parcelable {
    private int muster_id;
    private int user_id;
    public Usershow_tbl usershow_tbl;
    private int venues_id;
    private Venues_tbl venues_tbl;

    private int muster_number;
    private Timestamp muster_date;
    private Timestamp muster_time;
    private int muster_length;
    private String muster_note;
    private String muster_state;
    private String muster_type;
    public Muster_tbl() {

    }

    protected Muster_tbl(Parcel in) {
        muster_id = in.readInt();
        user_id = in.readInt();
        usershow_tbl = in.readParcelable(Usershow_tbl.class.getClassLoader());
        venues_id = in.readInt();
        venues_tbl = in.readParcelable(Venues_tbl.class.getClassLoader());
        muster_number = in.readInt();
        muster_length = in.readInt();
        muster_note = in.readString();
        muster_state = in.readString();
        muster_type = in.readString();
    }

    public static final Creator<Muster_tbl> CREATOR = new Creator<Muster_tbl>() {
        @Override
        public Muster_tbl createFromParcel(Parcel in) {
            return new Muster_tbl(in);
        }

        @Override
        public Muster_tbl[] newArray(int size) {
            return new Muster_tbl[size];
        }
    };

    public Usershow_tbl getUsershow_tbl() {
        return usershow_tbl;
    }

    public void setUsershow_tbl(Usershow_tbl usershow_tbl) {
        this.usershow_tbl = usershow_tbl;
    }

    public Muster_tbl(Timestamp muster_date, int muster_id, int muster_length, String muster_note, int muster_number, String muster_state, Timestamp muster_time, Usershow_tbl usershow_tbl, Venues_tbl venues_tbl,String muster_type) {

        this.muster_date = muster_date;
        this.muster_id = muster_id;
        this.muster_length = muster_length;
        this.muster_note = muster_note;
        this.muster_number = muster_number;
        this.muster_state = muster_state;
        this.muster_time = muster_time;

        this.venues_tbl = venues_tbl;
        this.muster_type = muster_type;
        this.usershow_tbl=usershow_tbl;

    }

// public Muster_tbl(Timestamp muster_date, int muster_id, int muster_length, String muster_note, int muster_number, String muster_state, Timestamp muster_time, int user_id, int venues_id,String muster_type) {
//
//
//    	this.muster_date = muster_date;
//        this.muster_id = muster_id;
//        this.muster_length = muster_length;
//        this.muster_note = muster_note;
//        this.muster_number = muster_number;
//        this.muster_state = muster_state;
//        this.muster_time = muster_time;
//        this.user_id = user_id;
//        this.venues_id = venues_id;
//        this.muster_type = muster_type;
    //   }

    public Timestamp getMuster_date() {
        return muster_date;
    }

    public void setMuster_date(Timestamp muster_date) {
        this.muster_date = muster_date;
    }

    public int getMuster_id() {
        return muster_id;
    }

    public void setMuster_id(int muster_id) {
        this.muster_id = muster_id;
    }

    public int getMuster_length() {
        return muster_length;
    }

    public void setMuster_length(int muster_length) {
        this.muster_length = muster_length;
    }

    public String getMuster_note() {
        return muster_note;
    }

    public void setMuster_note(String muster_note) {
        this.muster_note = muster_note;
    }

    public int getMuster_number() {
        return muster_number;
    }

    public void setMuster_number(int muster_number) {
        this.muster_number = muster_number;
    }

    public String getMuster_state() {
        return muster_state;
    }

    public void setMuster_state(String muster_state) {
        this.muster_state = muster_state;
    }

    public Timestamp getMuster_time() {
        return muster_time;
    }

    public void setMuster_time(Timestamp muster_time) {
        this.muster_time = muster_time;
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
    public String getMuster_type() {
        return muster_type;
    }

    public void setMuster_type(String muster_type) {
        this.muster_type = muster_type;
    }

    public Venues_tbl getVenues_tbl() {
        return venues_tbl;
    }

    public void setVenues_tbl(Venues_tbl venues_tbl) {
        this.venues_tbl = venues_tbl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(muster_id);
        dest.writeInt(user_id);
        dest.writeParcelable(usershow_tbl, flags);
        dest.writeInt(venues_id);
        dest.writeParcelable(venues_tbl, flags);
        dest.writeInt(muster_number);
        dest.writeInt(muster_length);
        dest.writeString(muster_note);
        dest.writeString(muster_state);
        dest.writeString(muster_type);
    }
}
