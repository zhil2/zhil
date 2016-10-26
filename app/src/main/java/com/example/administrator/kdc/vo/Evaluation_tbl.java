package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Evaluation_tbl implements Parcelable {
    private int evaluation_id;
    private int evaluation_praise;
    private int user_id;
    private int evaluation_type;
    private int evaluation_object;

    public Evaluation_tbl(int evaluation_id, int evaluation_object, int evaluation_praise, int evaluation_type, int user_id) {
        this.evaluation_id = evaluation_id;
        this.evaluation_object = evaluation_object;
        this.evaluation_praise = evaluation_praise;
        this.evaluation_type = evaluation_type;
        this.user_id = user_id;
    }

    protected Evaluation_tbl(Parcel in) {
        evaluation_id = in.readInt();
        evaluation_praise = in.readInt();
        user_id = in.readInt();
        evaluation_type = in.readInt();
        evaluation_object = in.readInt();
    }

    public static final Creator<Evaluation_tbl> CREATOR = new Creator<Evaluation_tbl>() {
        @Override
        public Evaluation_tbl createFromParcel(Parcel in) {
            return new Evaluation_tbl(in);
        }

        @Override
        public Evaluation_tbl[] newArray(int size) {
            return new Evaluation_tbl[size];
        }
    };

    public Evaluation_tbl() {

    }

    public int getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(int evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    public int getEvaluation_object() {
        return evaluation_object;
    }

    public void setEvaluation_object(int evaluation_object) {
        this.evaluation_object = evaluation_object;
    }

    public int getEvaluation_praise() {
        return evaluation_praise;
    }

    public void setEvaluation_praise(int evaluation_praise) {
        this.evaluation_praise = evaluation_praise;
    }

    public int getEvaluation_type() {
        return evaluation_type;
    }

    public void setEvaluation_type(int evaluation_type) {
        this.evaluation_type = evaluation_type;
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
        dest.writeInt(evaluation_id);
        dest.writeInt(evaluation_praise);
        dest.writeInt(user_id);
        dest.writeInt(evaluation_type);
        dest.writeInt(evaluation_object);
    }
}
