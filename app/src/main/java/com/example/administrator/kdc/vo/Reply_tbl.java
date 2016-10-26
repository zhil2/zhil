package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Reply_tbl implements Parcelable {
    private int reply_id;
    private Usershow_tbl usershow_tbl;
    private String reply_picture;
    private String reply_text;
    private Timestamp reply_date;
    private int venues_id;
    private int reply_end;

    public Reply_tbl(Timestamp reply_date, int reply_end, int reply_id, String reply_picture, String reply_text, Usershow_tbl usershow_tbl, int venues_id) {
        this.reply_date = reply_date;
        this.reply_end = reply_end;
        this.reply_id = reply_id;
        this.reply_picture = reply_picture;
        this.reply_text = reply_text;
        this.usershow_tbl = usershow_tbl;
        this.venues_id = venues_id;
    }


    protected Reply_tbl(Parcel in) {
        reply_id = in.readInt();
        usershow_tbl = in.readParcelable(Usershow_tbl.class.getClassLoader());
        reply_picture = in.readString();
        reply_text = in.readString();
        venues_id = in.readInt();
        reply_end = in.readInt();
    }

    public static final Creator<Reply_tbl> CREATOR = new Creator<Reply_tbl>() {
        @Override
        public Reply_tbl createFromParcel(Parcel in) {
            return new Reply_tbl(in);
        }

        @Override
        public Reply_tbl[] newArray(int size) {
            return new Reply_tbl[size];
        }
    };

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public Usershow_tbl getUsershow_tbl() {
        return usershow_tbl;
    }

    public void setUsershow_tbl(Usershow_tbl user_id) {
        this.usershow_tbl = usershow_tbl;
    }

    public String getReply_picture() {
        return reply_picture;
    }

    public void setReply_picture(String reply_picture) {
        this.reply_picture = reply_picture;
    }

    public String getReply_text() {
        return reply_text;
    }

    public void setReply_text(String reply_text) {
        this.reply_text = reply_text;
    }

    public Timestamp getReply_date() {
        return reply_date;
    }

    public void setReply_date(Timestamp reply_date) {
        this.reply_date = reply_date;
    }

    public int getVenues_id() {
        return venues_id;
    }

    public void setVenues_id(int venues_id) {
        this.venues_id = venues_id;
    }

    public int getReply_end() {
        return reply_end;
    }

    public void setReply_end(int reply_end) {
        this.reply_end = reply_end;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reply_id);
        dest.writeParcelable(usershow_tbl, flags);
        dest.writeString(reply_picture);
        dest.writeString(reply_text);
        dest.writeInt(venues_id);
        dest.writeInt(reply_end);
    }
}
