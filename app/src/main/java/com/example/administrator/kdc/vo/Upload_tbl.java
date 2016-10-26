package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Upload_tbl implements Parcelable {
    private double upload_latitude;
    private double upload_longitude;
    private int upload_id;
    private String upload_name;
    private int upload_ceiling;
    private int upload_current;
    private int address_id;
    private int upload_type;
    private String upload_portrait;
    private int user_id;
    private int upload_number;
    private double upload_price;
    private String upload_txt;
    private int upload_state;

    public Upload_tbl(double upload_longitude,double upload_latitude,int address_id, int upload_ceiling, int upload_current, int upload_id, String upload_name, int upload_number, String upload_portrait, double upload_price, int upload_state, String upload_txt, int upload_type, int user_id) {
        this.upload_longitude = upload_longitude;
        this.upload_latitude = upload_latitude;
        this.address_id = address_id;
        this.upload_ceiling = upload_ceiling;
        this.upload_current = upload_current;
        this.upload_id = upload_id;
        this.upload_name = upload_name;
        this.upload_number = upload_number;
        this.upload_portrait = upload_portrait;
        this.upload_price = upload_price;
        this.upload_state = upload_state;
        this.upload_txt = upload_txt;
        this.upload_type = upload_type;
        this.user_id = user_id;
    }


    protected Upload_tbl(Parcel in) {
        upload_latitude = in.readDouble();
        upload_longitude = in.readDouble();
        upload_id = in.readInt();
        upload_name = in.readString();
        upload_ceiling = in.readInt();
        upload_current = in.readInt();
        address_id = in.readInt();
        upload_type = in.readInt();
        upload_portrait = in.readString();
        user_id = in.readInt();
        upload_number = in.readInt();
        upload_price = in.readDouble();
        upload_txt = in.readString();
        upload_state = in.readInt();
    }

    public static final Creator<Upload_tbl> CREATOR = new Creator<Upload_tbl>() {
        @Override
        public Upload_tbl createFromParcel(Parcel in) {
            return new Upload_tbl(in);
        }

        @Override
        public Upload_tbl[] newArray(int size) {
            return new Upload_tbl[size];
        }
    };

    public double getUpload_latitude() {
        return upload_latitude;
    }


    public void setUpload_latitude(double upload_latitude) {
        this.upload_latitude = upload_latitude;
    }


    public double getUpload_longitude() {
        return upload_longitude;
    }


    public void setUpload_longitude(double upload_longitude) {
        this.upload_longitude = upload_longitude;
    }


    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getUpload_ceiling() {
        return upload_ceiling;
    }

    public void setUpload_ceiling(int upload_ceiling) {
        this.upload_ceiling = upload_ceiling;
    }

    public int getUpload_current() {
        return upload_current;
    }

    public void setUpload_current(int upload_current) {
        this.upload_current = upload_current;
    }

    public int getUpload_id() {
        return upload_id;
    }

    public void setUpload_id(int upload_id) {
        this.upload_id = upload_id;
    }

    public String getUpload_name() {
        return upload_name;
    }

    public void setUpload_name(String upload_name) {
        this.upload_name = upload_name;
    }

    public int getUpload_number() {
        return upload_number;
    }

    public void setUpload_number(int upload_number) {
        this.upload_number = upload_number;
    }

    public String getUpload_portrait() {
        return upload_portrait;
    }

    public void setUpload_portrait(String upload_portrait) {
        this.upload_portrait = upload_portrait;
    }

    public double getUpload_price() {
        return upload_price;
    }

    public void setUpload_price(double upload_price) {
        this.upload_price = upload_price;
    }

    public int getUpload_state() {
        return upload_state;
    }

    public void setUpload_state(int upload_state) {
        this.upload_state = upload_state;
    }

    public String getUpload_txt() {
        return upload_txt;
    }

    public void setUpload_txt(String upload_txt) {
        this.upload_txt = upload_txt;
    }

    public int getUpload_type() {
        return upload_type;
    }

    public void setUpload_type(int upload_type) {
        this.upload_type = upload_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(upload_latitude);
        dest.writeDouble(upload_longitude);
        dest.writeInt(upload_id);
        dest.writeString(upload_name);
        dest.writeInt(upload_ceiling);
        dest.writeInt(upload_current);
        dest.writeInt(address_id);
        dest.writeInt(upload_type);
        dest.writeString(upload_portrait);
        dest.writeInt(user_id);
        dest.writeInt(upload_number);
        dest.writeDouble(upload_price);
        dest.writeString(upload_txt);
        dest.writeInt(upload_state);
    }
}
