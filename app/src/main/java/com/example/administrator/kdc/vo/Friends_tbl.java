package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Friends_tbl implements Parcelable {
    private int friends_id;
    private int user_id;
    private int friend_id1;
    Usershow_tbl userDetail;
    private String friends_type;

    public Friends_tbl(int friends_id, int user_id, int friend_id1, Usershow_tbl userDetail, String friends_type) {
        super();
        this.friends_id = friends_id;
        this.user_id = user_id;
        this.friend_id1 = friend_id1;
        this.userDetail = userDetail;
        this.friends_type = friends_type;
    }

    public Friends_tbl(int friend_id1, int friends_id, String friends_type, int user_id) {
        this.friend_id1 = friend_id1;
        this.friends_id = friends_id;
        this.friends_type = friends_type;
        this.user_id = user_id;
    }

    protected Friends_tbl(Parcel in) {
        friends_id = in.readInt();
        user_id = in.readInt();
        friend_id1 = in.readInt();
        userDetail = in.readParcelable(Usershow_tbl.class.getClassLoader());
        friends_type = in.readString();
    }

    public static final Creator<Friends_tbl> CREATOR = new Creator<Friends_tbl>() {
        @Override
        public Friends_tbl createFromParcel(Parcel in) {
            return new Friends_tbl(in);
        }

        @Override
        public Friends_tbl[] newArray(int size) {
            return new Friends_tbl[size];
        }
    };

    public int getFriend_id1() {
        return friend_id1;
    }

    public void setFriend_id1(int friend_id1) {
        this.friend_id1 = friend_id1;
    }

    public int getFriends_id() {
        return friends_id;
    }

    public void setFriends_id(int friends_id) {
        this.friends_id = friends_id;
    }

    public String getFriends_type() {
        return friends_type;
    }

    public void setFriends_type(String friends_type) {
        this.friends_type = friends_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public Usershow_tbl getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(Usershow_tbl userDetail) {
        this.userDetail = userDetail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(friends_id);
        dest.writeInt(user_id);
        dest.writeInt(friend_id1);
        dest.writeParcelable(userDetail, flags);
        dest.writeString(friends_type);
    }
}
