package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Post_tbl implements Parcelable {
    private int post_id;
    private int post_user_id;
    private Usershow_tbl usershow_tbl;
    private String post_name;
    private int post_click;
    private int post_type;
    private int community_id;
    private String post_picture;
    private String post_text;
    private Timestamp post_time;
    private Timestamp endrely_time;
    public Post_tbl() {
    }

    public Post_tbl(int post_user_id, Usershow_tbl usershow_tbl, String post_name, int post_click, int post_type, int community_id, String post_picture, String post_text, Timestamp post_time, Timestamp endrely_time) {
        this.post_user_id = post_user_id;
        this.usershow_tbl = usershow_tbl;
        this.post_name = post_name;
        this.post_click = post_click;
        this.post_type = post_type;
        this.community_id = community_id;
        this.post_picture = post_picture;
        this.post_text = post_text;
        this.post_time = post_time;
        this.endrely_time = endrely_time;
    }

    public Post_tbl(int post_id, int post_user_id, Usershow_tbl usershow_tbl, String post_name, int post_click, int post_type, int community_id, String post_picture, String post_text, Timestamp post_time, Timestamp endrely_time) {
        this.post_id = post_id;
        this.post_user_id = post_user_id;
        this.usershow_tbl = usershow_tbl;
        this.post_name = post_name;
        this.post_click = post_click;
        this.post_type = post_type;
        this.community_id = community_id;
        this.post_picture = post_picture;
        this.post_text = post_text;
        this.post_time = post_time;
        this.endrely_time = endrely_time;
    }

    public Usershow_tbl getUsershow_tbl() {
        return usershow_tbl;
    }

    public void setUsershow_tbl(Usershow_tbl usershow_tbl) {
        this.usershow_tbl = usershow_tbl;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getPost_user_id() {
        return post_user_id;
    }

    public void setPost_user_id(int post_user_id) {
        this.post_user_id = post_user_id;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public int getPost_click() {
        return post_click;
    }

    public void setPost_click(int post_click) {
        this.post_click = post_click;
    }

    public int getPost_type() {
        return post_type;
    }

    public void setPost_type(int post_type) {
        this.post_type = post_type;
    }

    public int getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(int community_id) {
        this.community_id = community_id;
    }

    public String getPost_picture() {
        return post_picture;
    }

    public void setPost_picture(String post_picture) {
        this.post_picture = post_picture;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public Timestamp getPost_time() {
        return post_time;
    }

    public void setPost_time(Timestamp post_time) {
        this.post_time = post_time;
    }

    public Timestamp getEndrely_time() {
        return endrely_time;
    }

    public void setEndrely_time(Timestamp endrely_time) {
        this.endrely_time = endrely_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.post_id);
        dest.writeInt(this.post_user_id);
        dest.writeParcelable(this.usershow_tbl, flags);
        dest.writeString(this.post_name);
        dest.writeInt(this.post_click);
        dest.writeInt(this.post_type);
        dest.writeInt(this.community_id);
        dest.writeString(this.post_picture);
        dest.writeString(this.post_text);
        dest.writeSerializable(this.post_time);
        dest.writeSerializable(this.endrely_time);
    }

    protected Post_tbl(Parcel in) {
        this.post_id = in.readInt();
        this.post_user_id = in.readInt();
        this.usershow_tbl = in.readParcelable(Usershow_tbl.class.getClassLoader());
        this.post_name = in.readString();
        this.post_click = in.readInt();
        this.post_type = in.readInt();
        this.community_id = in.readInt();
        this.post_picture = in.readString();
        this.post_text = in.readString();
        this.post_time = (Timestamp) in.readSerializable();
        this.endrely_time = (Timestamp) in.readSerializable();
    }

    public static final Creator<Post_tbl> CREATOR = new Creator<Post_tbl>() {
        @Override
        public Post_tbl createFromParcel(Parcel source) {
            return new Post_tbl(source);
        }

        @Override
        public Post_tbl[] newArray(int size) {
            return new Post_tbl[size];
        }
    };
}
