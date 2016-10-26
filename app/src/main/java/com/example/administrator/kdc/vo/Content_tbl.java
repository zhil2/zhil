package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Content_tbl implements Parcelable {
    private int content_id;//内容id
    public static final int friends_id_friend_id= 0;
    public static final int friends_id_user_id = 1;
    private int friends_id;
    private String content_picture;//图片
    private String content_text;//文字
    private Timestamp content_date;//日期


    public Content_tbl(Timestamp content_date, int content_id, String content_picture, String content_text,int friends_id) {
        this.content_date = content_date;
        this.content_id = content_id;
        this.content_picture = content_picture;
        this.content_text = content_text;
        this.friends_id = friends_id;
    }

    public Content_tbl(int friends_id, String content_text) {
        this.friends_id = friends_id;
        this.content_text = content_text;
    }

    protected Content_tbl(Parcel in) {
        content_id = in.readInt();
        friends_id = in.readInt();
        content_picture = in.readString();
        content_text = in.readString();
    }

    public static final Creator<Content_tbl> CREATOR = new Creator<Content_tbl>() {
        @Override
        public Content_tbl createFromParcel(Parcel in) {
            return new Content_tbl(in);
        }

        @Override
        public Content_tbl[] newArray(int size) {
            return new Content_tbl[size];
        }
    };

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public static int getFriends_id_friend_id() {
        return friends_id_friend_id;
    }

    public static int getFriends_id_user_id() {
        return friends_id_user_id;
    }

    public int getFriends_id() {
        return friends_id;
    }

    public void setFriends_id(int friends_id) {
        this.friends_id = friends_id;
    }

    public String getContent_picture() {
        return content_picture;
    }

    public void setContent_picture(String content_picture) {
        this.content_picture = content_picture;
    }

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public Timestamp getContent_date() {
        return content_date;
    }

    public void setContent_date(Timestamp content_date) {
        this.content_date = content_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(content_id);
        dest.writeInt(friends_id);
        dest.writeString(content_picture);
        dest.writeString(content_text);
    }
}
