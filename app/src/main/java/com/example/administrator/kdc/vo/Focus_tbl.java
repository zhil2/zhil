package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Focus_tbl implements Parcelable {
    private int focus_id;
    private int foucs_type;
    private int exceptional_object;
    private Community_tbl community_tbl;
    private Friends_tbl friends_tbl;
    private int user_id;
    private int focus_stype;
    public Focus_tbl(){
    }

    public Focus_tbl(int focus_id, int foucs_type, int exceptional_object, Friends_tbl friends_tbl, int user_id, int focus_stype) {
        this.focus_id = focus_id;
        this.foucs_type = foucs_type;
        this.exceptional_object = exceptional_object;
        this.friends_tbl = friends_tbl;
        this.user_id = user_id;
        this.focus_stype = focus_stype;
    }
    public Focus_tbl(int focus_id, int foucs_type, int exceptional_object, Community_tbl community_tbl, int user_id, int focus_stype) {
        this.focus_id = focus_id;
        this.foucs_type = foucs_type;
        this.exceptional_object = exceptional_object;
        this.community_tbl = community_tbl;
        this.user_id = user_id;
        this.focus_stype = focus_stype;
    }

    public int getFocus_id() {
        return focus_id;
    }

    public void setFocus_id(int focus_id) {
        this.focus_id = focus_id;
    }

    public int getFoucs_type() {
        return foucs_type;
    }

    public void setFoucs_type(int foucs_type) {
        this.foucs_type = foucs_type;
    }

    public int getExceptional_object() {
        return exceptional_object;
    }

    public void setExceptional_object(int exceptional_object) {
        this.exceptional_object = exceptional_object;
    }

    public Community_tbl getCommunity_tbl() {
        return community_tbl;
    }

    public void setCommunity_tbl(Community_tbl community_tbl) {
        this.community_tbl = community_tbl;
    }

    public Friends_tbl getFriends_tbl() {
        return friends_tbl;
    }

    public void setFriends_tbl(Friends_tbl friends_tbl) {
        this.friends_tbl = friends_tbl;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFocus_stype() {
        return focus_stype;
    }

    public void setFocus_stype(int focus_stype) {
        this.focus_stype = focus_stype;
    }

    //    public Focus_tbl(int foucs_type, int exceptional_object, int user_id, int focus_stype) {
//        this.foucs_type = foucs_type;
//        this.exceptional_object = exceptional_object;
//        this.user_id = user_id;
//        this.focus_stype = focus_stype;
//    }
//
//    public Focus_tbl(int focus_id, int foucs_type, int exceptional_object, int user_id, int focus_stype) {
//        this.focus_id = focus_id;
//        this.foucs_type = foucs_type;
//        this.exceptional_object = exceptional_object;
//        this.user_id = user_id;
//        this.focus_stype = focus_stype;
//    }
//
//    public int getFocus_id() {
//        return focus_id;
//    }
//
//    public void setFocus_id(int focus_id) {
//        this.focus_id = focus_id;
//    }
//
//    public int getFoucs_type() {
//        return foucs_type;
//    }
//
//    public void setFoucs_type(int foucs_type) {
//        this.foucs_type = foucs_type;
//    }
//
//    public int getExceptional_object() {
//        return exceptional_object;
//    }
//
//    public void setExceptional_object(int exceptional_object) {
//        this.exceptional_object = exceptional_object;
//    }
//
//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }
//
//    public int getFocus_stype() {
//        return focus_stype;
//    }
//
//    public void setFocus_stype(int focus_stype) {
//        this.focus_stype = focus_stype;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(this.focus_id);
//        dest.writeInt(this.foucs_type);
//        dest.writeInt(this.exceptional_object);
//        dest.writeInt(this.user_id);
//        dest.writeInt(this.focus_stype);
//    }
//
//    protected Focus_tbl(Parcel in) {
//        this.focus_id = in.readInt();
//        this.foucs_type = in.readInt();
//        this.exceptional_object = in.readInt();
//        this.user_id = in.readInt();
//        this.focus_stype = in.readInt();
//    }
//
//    public static final Creator<Focus_tbl> CREATOR = new Creator<Focus_tbl>() {
//        @Override
//        public Focus_tbl createFromParcel(Parcel source) {
//            return new Focus_tbl(source);
//        }
//
//        @Override
//        public Focus_tbl[] newArray(int size) {
//            return new Focus_tbl[size];
//        }
//    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.focus_id);
        dest.writeInt(this.foucs_type);
        dest.writeInt(this.exceptional_object);
        dest.writeParcelable(this.community_tbl, flags);
        dest.writeParcelable(this.friends_tbl, flags);
        dest.writeInt(this.user_id);
        dest.writeInt(this.focus_stype);
    }

    protected Focus_tbl(Parcel in) {
        this.focus_id = in.readInt();
        this.foucs_type = in.readInt();
        this.exceptional_object = in.readInt();
        this.community_tbl = in.readParcelable(Community_tbl.class.getClassLoader());
        this.friends_tbl = in.readParcelable(Friends_tbl.class.getClassLoader());
        this.user_id = in.readInt();
        this.focus_stype = in.readInt();
    }

    public static final Creator<Focus_tbl> CREATOR = new Creator<Focus_tbl>() {
        @Override
        public Focus_tbl createFromParcel(Parcel source) {
            return new Focus_tbl(source);
        }

        @Override
        public Focus_tbl[] newArray(int size) {
            return new Focus_tbl[size];
        }
    };
}
