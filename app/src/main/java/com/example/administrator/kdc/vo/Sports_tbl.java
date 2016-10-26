package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Sports_tbl implements Parcelable {
    private int sports_id;
    private Sportstype_tbl sportstype_tbl;
    private int sports_type;
    private Community_tbl community_tbl;
    private User_tbl user_tbl;

    public Sports_tbl(int sports_id, Sportstype_tbl sportstype_tbl,
                      int sports_type, User_tbl user_tbl) {
        super();
        this.sports_id = sports_id;
        this.sportstype_tbl = sportstype_tbl;
        this.sports_type = sports_type;
        this.user_tbl = user_tbl;
    }
    public Sports_tbl(int sports_id, Sportstype_tbl sportstype_tbl,
                      int sports_type, Community_tbl community_tbl) {
        super();
        this.sports_id = sports_id;
        this.sportstype_tbl = sportstype_tbl;
        this.sports_type = sports_type;
        this.community_tbl = community_tbl;
    }

    protected Sports_tbl(Parcel in) {
        sports_id = in.readInt();
        sportstype_tbl = in.readParcelable(Sportstype_tbl.class.getClassLoader());
        sports_type = in.readInt();
        community_tbl = in.readParcelable(Community_tbl.class.getClassLoader());
        user_tbl = in.readParcelable(User_tbl.class.getClassLoader());
    }

    public static final Creator<Sports_tbl> CREATOR = new Creator<Sports_tbl>() {
        @Override
        public Sports_tbl createFromParcel(Parcel in) {
            return new Sports_tbl(in);
        }

        @Override
        public Sports_tbl[] newArray(int size) {
            return new Sports_tbl[size];
        }
    };

    public int getSports_id() {
        return sports_id;
    }
    public void setSports_id(int sports_id) {
        this.sports_id = sports_id;
    }
    public Sportstype_tbl getSportstype_tbl() {
        return sportstype_tbl;
    }
    public void setSportstype_tbl(Sportstype_tbl sportstype_tbl) {
        this.sportstype_tbl = sportstype_tbl;
    }
    public int getSports_type() {
        return sports_type;
    }
    public void setSports_type(int sports_type) {
        this.sports_type = sports_type;
    }
    public Community_tbl getCommunity_tbl() {
        return community_tbl;
    }
    public void setCommunity_tbl(Community_tbl community_tbl) {
        this.community_tbl = community_tbl;
    }
    public User_tbl getUser_tbl() {
        return user_tbl;
    }
    public void setUser_tbl(User_tbl user_tbl) {
        this.user_tbl = user_tbl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sports_id);
        dest.writeParcelable(sportstype_tbl, flags);
        dest.writeInt(sports_type);
        dest.writeParcelable(community_tbl, flags);
        dest.writeParcelable(user_tbl, flags);
    }
}
