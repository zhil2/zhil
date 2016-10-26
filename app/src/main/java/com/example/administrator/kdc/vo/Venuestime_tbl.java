package com.example.administrator.kdc.vo;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Venuestime_tbl implements Parcelable {
    private int venuestime_id;
    private int venues_id;
    private String venuestime_kg;
    private String venuestime_bg;
    
    
    public Venuestime_tbl(){
    	
    }
    
    
	public Venuestime_tbl(int venuestime_id, int venues_id, String venuestime_kg, String venuestime_bg) {
		super();
		this.venuestime_id = venuestime_id;
		this.venues_id = venues_id;
		this.venuestime_kg = venuestime_kg;
		this.venuestime_bg = venuestime_bg;
	}


	protected Venuestime_tbl(Parcel in) {
		venuestime_id = in.readInt();
		venues_id = in.readInt();
		venuestime_kg = in.readString();
		venuestime_bg = in.readString();
	}

	public static final Creator<Venuestime_tbl> CREATOR = new Creator<Venuestime_tbl>() {
		@Override
		public Venuestime_tbl createFromParcel(Parcel in) {
			return new Venuestime_tbl(in);
		}

		@Override
		public Venuestime_tbl[] newArray(int size) {
			return new Venuestime_tbl[size];
		}
	};

	public int getVenuestime_id() {
		return venuestime_id;
	}
	public void setVenuestime_id(int venuestime_id) {
		this.venuestime_id = venuestime_id;
	}
	public int getVenues_id() {
		return venues_id;
	}
	public void setVenues_id(int venues_id) {
		this.venues_id = venues_id;
	}
	public String getVenuestime_kg() {
		return venuestime_kg;
	}
	public void setVenuestime_kg(String venuestime_kg) {
		this.venuestime_kg = venuestime_kg;
	}
	public String getVenuestime_bg() {
		return venuestime_bg;
	}
	public void setVenuestime_bg(String venuestime_bg) {
		this.venuestime_bg = venuestime_bg;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(venuestime_id);
		dest.writeInt(venues_id);
		dest.writeString(venuestime_kg);
		dest.writeString(venuestime_bg);
	}
}
