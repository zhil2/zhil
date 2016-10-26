package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Usershow_tbl implements Parcelable {
    private int usershow_id;
    private int user_id;
    private User_tbl user_tbl;
    private String usershow_name;
    private String usershow_sex;
    private int usershow_age;
    private Timestamp usershow_birthday;
    private String usershow_head;
    private int usershow_credit;
    private double usershow_money;
    private int address_id;

    public Usershow_tbl(){};

    public Usershow_tbl(int address_id, User_tbl user_tbl,String usershow_name,  String usershow_sex, int usershow_age, Timestamp usershow_birthday, String usershow_head, int usershow_credit, double usershow_money, int usershow_id) {
        this.address_id = address_id;
        this.user_tbl = user_tbl;
        this.usershow_age = usershow_age;
        this.usershow_birthday = usershow_birthday;
        this.usershow_credit = usershow_credit;
        this.usershow_head = usershow_head;
        this.usershow_id = usershow_id;
        this.usershow_money = usershow_money;
        this.usershow_name = usershow_name;
        this.usershow_sex = usershow_sex;
    }


    public Usershow_tbl(int address_id, int user_id,String usershow_name,  String usershow_sex, int usershow_age, Timestamp usershow_birthday, String usershow_head, int usershow_credit, double usershow_money, int usershow_id) {
        this.address_id = address_id;
        this.user_id = user_id;
        this.usershow_age = usershow_age;
        this.usershow_birthday = usershow_birthday;
        this.usershow_credit = usershow_credit;
        this.usershow_head = usershow_head;
        this.usershow_id = usershow_id;
        this.usershow_money = usershow_money;
        this.usershow_name = usershow_name;
        this.usershow_sex = usershow_sex;
    }


    protected Usershow_tbl(Parcel in) {
        usershow_id = in.readInt();
        user_id = in.readInt();
        user_tbl = in.readParcelable(User_tbl.class.getClassLoader());
        usershow_name = in.readString();
        usershow_sex = in.readString();
        usershow_age = in.readInt();
        usershow_head = in.readString();
        usershow_credit = in.readInt();
        usershow_money = in.readDouble();
        address_id = in.readInt();
    }

    public static final Creator<Usershow_tbl> CREATOR = new Creator<Usershow_tbl>() {
        @Override
        public Usershow_tbl createFromParcel(Parcel in) {
            return new Usershow_tbl(in);
        }

        @Override
        public Usershow_tbl[] newArray(int size) {
            return new Usershow_tbl[size];
        }
    };

    public User_tbl getUser_tbl() {
        return user_tbl;
    }

    public void setUser_tbl(User_tbl user_tbl) {
        this.user_tbl = user_tbl;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUsershow_age() {
        return usershow_age;
    }

    public void setUsershow_age(int usershow_age) {
        this.usershow_age = usershow_age;
    }

    public Timestamp getUsershow_birthday() {

            return usershow_birthday;

    }

    public void setUsershow_birthday(Timestamp usershow_birthday) {
        this.usershow_birthday = usershow_birthday;
    }

    public int getUsershow_credit() {
        return usershow_credit;
    }

    public void setUsershow_credit(int usershow_credit) {
        this.usershow_credit = usershow_credit;
    }

    public String getUsershow_head() {
        return usershow_head;
    }

    public void setUsershow_head(String usershow_head) {
        this.usershow_head = usershow_head;
    }

    public int getUsershow_id() {
        return usershow_id;
    }

    public void setUsershow_id(int usershow_id) {
        this.usershow_id = usershow_id;
    }

    public double getUsershow_money() {
        return usershow_money;
    }

    public void setUsershow_money(double usershow_money) {
        this.usershow_money = usershow_money;
    }

    public String getUsershow_name() {
        return usershow_name;
    }

    public void setUsershow_name(String usershow_name) {
        this.usershow_name = usershow_name;
    }

    public String getUsershow_sex() {
        return usershow_sex;
    }

    public void setUsershow_sex(String usershow_sex) {
        this.usershow_sex = usershow_sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(usershow_id);
        dest.writeInt(user_id);
        dest.writeParcelable(user_tbl, flags);
        dest.writeString(usershow_name);
        dest.writeString(usershow_sex);
        dest.writeInt(usershow_age);
        dest.writeString(usershow_head);
        dest.writeInt(usershow_credit);
        dest.writeDouble(usershow_money);
        dest.writeInt(address_id);
    }
}
