package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Sportstype_tbl implements Parcelable {
    private int sportstype_id;
    private String sportstype_name;
    private String sportstype_picture;

    public Sportstype_tbl(int sportstype_id, String sportstype_name, String sportstype_picture) {
        this.sportstype_id = sportstype_id;
        this.sportstype_name = sportstype_name;
        this.sportstype_picture = sportstype_picture;
    }

    protected Sportstype_tbl(Parcel in) {
        sportstype_id = in.readInt();
        sportstype_name = in.readString();
        sportstype_picture = in.readString();
    }

    public static final Creator<Sportstype_tbl> CREATOR = new Creator<Sportstype_tbl>() {
        @Override
        public Sportstype_tbl createFromParcel(Parcel in) {
            return new Sportstype_tbl(in);
        }

        @Override
        public Sportstype_tbl[] newArray(int size) {
            return new Sportstype_tbl[size];
        }
    };

    public int getSportstype_id() {
        return sportstype_id;
    }

    public void setSportstype_id(int sportstype_id) {
        this.sportstype_id = sportstype_id;
    }

    public String getSportstype_name() {
        return sportstype_name;
    }

    public void setSportstype_name(String sportstype_name) {
        this.sportstype_name = sportstype_name;
    }

    public String getSportstype_picture() {
        return sportstype_picture;
    }

    public void setSportstype_picture(String sportstype_picture) {
        this.sportstype_picture = sportstype_picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sportstype_id);
        dest.writeString(sportstype_name);
        dest.writeString(sportstype_picture);
    }
}
