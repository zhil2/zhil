package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Venuesshow_tbl implements Parcelable {
    private int venuesshow_id;
    private String venuesshow_portrait;
    private int user_id;
    private int venuesshow_number;
    private double venuesshow_price;
    private String venuesshow_txt;

    public Venuesshow_tbl(int user_id, int venuesshow_id, int venuesshow_number, String venuesshow_portrait, double venuesshow_price, String venuesshow_txt) {
        this.user_id = user_id;
        this.venuesshow_id = venuesshow_id;
        this.venuesshow_number = venuesshow_number;
        this.venuesshow_portrait = venuesshow_portrait;
        this.venuesshow_price = venuesshow_price;
        this.venuesshow_txt = venuesshow_txt;
    }




    protected Venuesshow_tbl(Parcel in) {
        venuesshow_id = in.readInt();
        venuesshow_portrait = in.readString();
        user_id = in.readInt();
        venuesshow_number = in.readInt();
        venuesshow_price = in.readDouble();
        venuesshow_txt = in.readString();
    }

    public static final Creator<Venuesshow_tbl> CREATOR = new Creator<Venuesshow_tbl>() {
        @Override
        public Venuesshow_tbl createFromParcel(Parcel in) {
            return new Venuesshow_tbl(in);
        }

        @Override
        public Venuesshow_tbl[] newArray(int size) {
            return new Venuesshow_tbl[size];
        }
    };

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVenuesshow_id() {
        return venuesshow_id;
    }

    public void setVenuesshow_id(int venuesshow_id) {
        this.venuesshow_id = venuesshow_id;
    }

    public int getVenuesshow_number() {
        return venuesshow_number;
    }

    public void setVenuesshow_number(int venuesshow_number) {
        this.venuesshow_number = venuesshow_number;
    }

    public String getVenuesshow_portrait() {
        return venuesshow_portrait;
    }

    public void setVenuesshow_portrait(String venuesshow_portrait) {
        this.venuesshow_portrait = venuesshow_portrait;
    }

    public double getVenuesshow_price() {
        return venuesshow_price;
    }

    public void setVenuesshow_price(double venuesshow_price) {
        this.venuesshow_price = venuesshow_price;
    }

    public String getVenuesshow_txt() {
        return venuesshow_txt;
    }

    public void setVenuesshow_txt(String venuesshow_txt) {
        this.venuesshow_txt = venuesshow_txt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(venuesshow_id);
        dest.writeString(venuesshow_portrait);
        dest.writeInt(user_id);
        dest.writeInt(venuesshow_number);
        dest.writeDouble(venuesshow_price);
        dest.writeString(venuesshow_txt);
    }
}
