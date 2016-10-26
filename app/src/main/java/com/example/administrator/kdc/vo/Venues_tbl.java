package com.example.administrator.kdc.vo;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kskjf on 2016/9/20.
 */
public class Venues_tbl implements Parcelable {
	 private static final long serialVersionUID = 8633299996744734593L;

	    private double latitude;//纬度
	    private double longitude;//经度
	    private int venues_id;
	    private String venues_name;
	    private int venues_ceiling;
	    private int venues_current;
	 //   private int address_id;
	    private Address_tbl address_tbl;
	//    private int venuesshow_id;
	    private Venuesshow_tbl venuesshow_tbl;
	    private int venues_type;
	    private int venues_yes;
	    private int venues_no;

	public Venues_tbl(){

	}

	public Venues_tbl(double latitude, double longitude, int venues_id, String venues_name, int venues_ceiling, int venues_current, Address_tbl address_tbl, Venuesshow_tbl venuesshow_tbl, int venues_type, int venues_yes, int venues_no) {
	        this.latitude = latitude;
	        this.longitude = longitude;
	        this.venues_id = venues_id;
	        this.venues_name = venues_name;
	        this.venues_ceiling = venues_ceiling;
	        this.venues_current = venues_current;
	        this.address_tbl = address_tbl;
	        this.venuesshow_tbl = venuesshow_tbl;
	        this.venues_type = venues_type;
	        this.venues_yes = venues_yes;
	        this.venues_no = venues_no;
	    }

	protected Venues_tbl(Parcel in) {
		latitude = in.readDouble();
		longitude = in.readDouble();
		venues_id = in.readInt();
		venues_name = in.readString();
		venues_ceiling = in.readInt();
		venues_current = in.readInt();
		address_tbl = in.readParcelable(Address_tbl.class.getClassLoader());
		venuesshow_tbl = in.readParcelable(Venuesshow_tbl.class.getClassLoader());
		venues_type = in.readInt();
		venues_yes = in.readInt();
		venues_no = in.readInt();
	}

	public static final Creator<Venues_tbl> CREATOR = new Creator<Venues_tbl>() {
		@Override
		public Venues_tbl createFromParcel(Parcel in) {
			return new Venues_tbl(in);
		}

		@Override
		public Venues_tbl[] newArray(int size) {
			return new Venues_tbl[size];
		}
	};

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getVenues_id() {
		return venues_id;
	}

	public void setVenues_id(int venues_id) {
		this.venues_id = venues_id;
	}

	public String getVenues_name() {
		return venues_name;
	}

	public void setVenues_name(String venues_name) {
		this.venues_name = venues_name;
	}

	public int getVenues_ceiling() {
		return venues_ceiling;
	}

	public void setVenues_ceiling(int venues_ceiling) {
		this.venues_ceiling = venues_ceiling;
	}

	public int getVenues_current() {
		return venues_current;
	}

	public void setVenues_current(int venues_current) {
		this.venues_current = venues_current;
	}

	public Address_tbl getAddress_tbl() {
		return address_tbl;
	}

	public void setAddress_tbl(Address_tbl address_tbl) {
		this.address_tbl = address_tbl;
	}

	public Venuesshow_tbl getVenuesshow_tbl() {
		return venuesshow_tbl;
	}

	public void setVenuesshow_tbl(Venuesshow_tbl venuesshow_tbl) {
		this.venuesshow_tbl = venuesshow_tbl;
	}

	public int getVenues_type() {
		return venues_type;
	}

	public void setVenues_type(int venues_type) {
		this.venues_type = venues_type;
	}

	public int getVenues_yes() {
		return venues_yes;
	}

	public void setVenues_yes(int venues_yes) {
		this.venues_yes = venues_yes;
	}

	public int getVenues_no() {
		return venues_no;
	}

	public void setVenues_no(int venues_no) {
		this.venues_no = venues_no;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeInt(venues_id);
		dest.writeString(venues_name);
		dest.writeInt(venues_ceiling);
		dest.writeInt(venues_current);
		dest.writeParcelable(address_tbl, flags);
		dest.writeParcelable(venuesshow_tbl, flags);
		dest.writeInt(venues_type);
		dest.writeInt(venues_yes);
		dest.writeInt(venues_no);
	}
}
