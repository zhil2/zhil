package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Address_tbl implements Parcelable {
    private int address_id;
    private String address_province;
    private String address_city;
    private String address_county;
    private String address_town;
    private String address_show;

    public Address_tbl(String address_city, String address_county, int address_id, String address_province, String address_show, String address_town) {
        this.address_city = address_city;
        this.address_county = address_county;
        this.address_id = address_id;
        this.address_province = address_province;
        this.address_show = address_show;
        this.address_town = address_town;
    }

    protected Address_tbl(Parcel in) {
        address_id = in.readInt();
        address_province = in.readString();
        address_city = in.readString();
        address_county = in.readString();
        address_town = in.readString();
        address_show = in.readString();
    }

    public static final Creator<Address_tbl> CREATOR = new Creator<Address_tbl>() {
        @Override
        public Address_tbl createFromParcel(Parcel in) {
            return new Address_tbl(in);
        }

        @Override
        public Address_tbl[] newArray(int size) {
            return new Address_tbl[size];
        }
    };

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_county() {
        return address_county;
    }

    public void setAddress_county(String address_county) {
        this.address_county = address_county;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getAddress_province() {
        return address_province;
    }

    public void setAddress_province(String address_province) {
        this.address_province = address_province;
    }

    public String getAddress_show() {
        return address_show;
    }

    public void setAddress_show(String address_show) {
        this.address_show = address_show;
    }

    public String getAddress_town() {
        return address_town;
    }

    public void setAddress_town(String address_town) {
        this.address_town = address_town;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(address_id);
        dest.writeString(address_province);
        dest.writeString(address_city);
        dest.writeString(address_county);
        dest.writeString(address_town);
        dest.writeString(address_show);
    }
}
