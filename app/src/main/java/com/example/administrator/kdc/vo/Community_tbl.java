package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Community_tbl implements Parcelable {
    private int community_id;
    private String community_name;

    public Community_tbl(int community_id, String community_name) {
        this.community_id = community_id;
        this.community_name = community_name;
    }

    protected Community_tbl(Parcel in) {
        community_id = in.readInt();
        community_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(community_id);
        dest.writeString(community_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Community_tbl> CREATOR = new Creator<Community_tbl>() {
        @Override
        public Community_tbl createFromParcel(Parcel in) {
            return new Community_tbl(in);
        }

        @Override
        public Community_tbl[] newArray(int size) {
            return new Community_tbl[size];
        }
    };

    public int getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(int community_id) {
        this.community_id = community_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }
}
