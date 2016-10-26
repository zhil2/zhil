package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Collection_tbl implements Parcelable {
    private int collection_id;
    private int user_id;
    private int collection_type;
    private int collection_object;

    public Collection_tbl(int collection_id, int collection_object, int collection_type, int user_id) {
        this.collection_id = collection_id;
        this.collection_object = collection_object;
        this.collection_type = collection_type;
        this.user_id = user_id;
    }

    protected Collection_tbl(Parcel in) {
        collection_id = in.readInt();
        user_id = in.readInt();
        collection_type = in.readInt();
        collection_object = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(collection_id);
        dest.writeInt(user_id);
        dest.writeInt(collection_type);
        dest.writeInt(collection_object);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Collection_tbl> CREATOR = new Creator<Collection_tbl>() {
        @Override
        public Collection_tbl createFromParcel(Parcel in) {
            return new Collection_tbl(in);
        }

        @Override
        public Collection_tbl[] newArray(int size) {
            return new Collection_tbl[size];
        }
    };

    public int getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(int collection_id) {
        this.collection_id = collection_id;
    }

    public int getCollection_object() {
        return collection_object;
    }

    public void setCollection_object(int collection_object) {
        this.collection_object = collection_object;
    }

    public int getCollection_type() {
        return collection_type;
    }

    public void setCollection_type(int collection_type) {
        this.collection_type = collection_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
