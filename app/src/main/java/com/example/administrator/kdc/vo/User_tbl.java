package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class User_tbl implements Parcelable {
    private int user_id;//用户id
    private int user_number;//用户账号
    private String user_pwd;//用户密码
    private String user_emal;//用户邮箱


    public User_tbl(int user_id, int user_number, String user_pwd, String user_emal) {
        this.user_id = user_id;
        this.user_number = user_number;
        this.user_pwd = user_pwd;
        this.user_emal = user_emal;
    }

    public User_tbl(){};

    public User_tbl(int user_id){ this.user_id = user_id;};

    public User_tbl(int user_number, String user_pwd){
        this.user_number = user_number;
        this.user_pwd = user_pwd;
    };

    protected User_tbl(Parcel in) {
        user_id = in.readInt();
        user_number = in.readInt();
        user_pwd = in.readString();
        user_emal = in.readString();
    }

    public static final Creator<User_tbl> CREATOR = new Creator<User_tbl>() {
        @Override
        public User_tbl createFromParcel(Parcel in) {
            return new User_tbl(in);
        }

        @Override
        public User_tbl[] newArray(int size) {
            return new User_tbl[size];
        }
    };

    public String getUser_emal() {
        return user_emal;
    }

    public void setUser_emal(String user_emal) {
        this.user_emal = user_emal;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_number() {
        return user_number;
    }

    public void setUser_number(int user_number) {
        this.user_number = user_number;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(user_id);
        dest.writeInt(user_number);
        dest.writeString(user_pwd);
        dest.writeString(user_emal);
    }
}
