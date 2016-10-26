package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Setup_tbl implements Parcelable {
    private int setup_id;
    private int user_id;
    private int setup_1;
    private int setup_2;
    private int setup_3;
    private int setup_4;
    private int setup_5;

    public Setup_tbl(int setup_1, int setup_2, int setup_3, int setup_4, int setup_5, int setup_id, int user_id) {
        this.setup_1 = setup_1;
        this.setup_2 = setup_2;
        this.setup_3 = setup_3;
        this.setup_4 = setup_4;
        this.setup_5 = setup_5;
        this.setup_id = setup_id;
        this.user_id = user_id;
    }

    protected Setup_tbl(Parcel in) {
        setup_id = in.readInt();
        user_id = in.readInt();
        setup_1 = in.readInt();
        setup_2 = in.readInt();
        setup_3 = in.readInt();
        setup_4 = in.readInt();
        setup_5 = in.readInt();
    }

    public static final Creator<Setup_tbl> CREATOR = new Creator<Setup_tbl>() {
        @Override
        public Setup_tbl createFromParcel(Parcel in) {
            return new Setup_tbl(in);
        }

        @Override
        public Setup_tbl[] newArray(int size) {
            return new Setup_tbl[size];
        }
    };

    public int getSetup_1() {
        return setup_1;
    }

    public void setSetup_1(int setup_1) {
        this.setup_1 = setup_1;
    }

    public int getSetup_2() {
        return setup_2;
    }

    public void setSetup_2(int setup_2) {
        this.setup_2 = setup_2;
    }

    public int getSetup_3() {
        return setup_3;
    }

    public void setSetup_3(int setup_3) {
        this.setup_3 = setup_3;
    }

    public int getSetup_4() {
        return setup_4;
    }

    public void setSetup_4(int setup_4) {
        this.setup_4 = setup_4;
    }

    public int getSetup_5() {
        return setup_5;
    }

    public void setSetup_5(int setup_5) {
        this.setup_5 = setup_5;
    }

    public int getSetup_id() {
        return setup_id;
    }

    public void setSetup_id(int setup_id) {
        this.setup_id = setup_id;
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
        dest.writeInt(setup_id);
        dest.writeInt(user_id);
        dest.writeInt(setup_1);
        dest.writeInt(setup_2);
        dest.writeInt(setup_3);
        dest.writeInt(setup_4);
        dest.writeInt(setup_5);
    }
}
