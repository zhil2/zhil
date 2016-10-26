package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Mustergo_tbl implements Parcelable {
    public int mustergo_id;
    public int muster_id;
    public int user_id;

    public Mustergo_tbl(int mustergo_id, int muster_id, int user_id) {
        this.mustergo_id = mustergo_id;
        this.muster_id = muster_id;
        this.user_id = user_id;
    }

    protected Mustergo_tbl(Parcel in) {
        mustergo_id = in.readInt();
        muster_id = in.readInt();
        user_id = in.readInt();
    }

    public static final Creator<Mustergo_tbl> CREATOR = new Creator<Mustergo_tbl>() {
        @Override
        public Mustergo_tbl createFromParcel(Parcel in) {
            return new Mustergo_tbl(in);
        }

        @Override
        public Mustergo_tbl[] newArray(int size) {
            return new Mustergo_tbl[size];
        }
    };

    public int getMustergo_id() {
        return mustergo_id;
    }

    public void setMustergo_id(int mustergo_id) {
        this.mustergo_id = mustergo_id;
    }

    public int getMuster_id() {
        return muster_id;
    }

    public void setMuster_id(int muster_id) {
        this.muster_id = muster_id;
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
        dest.writeInt(mustergo_id);
        dest.writeInt(muster_id);
        dest.writeInt(user_id);
    }
}
