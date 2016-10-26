package com.example.administrator.kdc.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class VC_tbl implements Parcelable {

	Venues_tbl venues_tbl;
	int flag;
	int flag2;

	public VC_tbl(){

	}

	public VC_tbl(Venues_tbl venues_tbl,int flag){
		this.venues_tbl=venues_tbl;
		this.flag=flag;
	}

	public VC_tbl(Venues_tbl venues_tbl,int flag,int flag2){
		this.venues_tbl=venues_tbl;
		this.flag=flag;
		this.flag2=flag2;
	}


	protected VC_tbl(Parcel in) {
		venues_tbl = in.readParcelable(Venues_tbl.class.getClassLoader());
		flag = in.readInt();
		flag2 = in.readInt();
	}

	public static final Creator<VC_tbl> CREATOR = new Creator<VC_tbl>() {
		@Override
		public VC_tbl createFromParcel(Parcel in) {
			return new VC_tbl(in);
		}

		@Override
		public VC_tbl[] newArray(int size) {
			return new VC_tbl[size];
		}
	};

	public int getFlag2() {
		return flag2;
	}

	public void setFlag2(int flag2) {
		this.flag2 = flag2;
	}

	public Venues_tbl getVenues_tbl() {
		return venues_tbl;
	}

	public void setVenues_tbl(Venues_tbl venues_tbl) {
		this.venues_tbl = venues_tbl;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(venues_tbl, flags);
		dest.writeInt(flag);
		dest.writeInt(flag2);
	}
}