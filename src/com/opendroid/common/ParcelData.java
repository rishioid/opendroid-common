package com.opendroid.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ParcelData implements Parcelable{

	public int id;
	public String name;
	
	public ParcelData() {
		// TODO Auto-generated constructor stub
	}
	
	/**
     * This will be used only by the MyCreator
     * @param source
     */
    public ParcelData(Parcel source){
          /*
           * Reconstruct from the Parcel
           */
          Log.v("Pacel", "ParcelData(Parcel source): time to put back parcel data");
          setId(source.readInt());
          setName(source.readString());
    }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getId());
	    dest.writeString(getName());
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

		@Override
		public ParcelData createFromParcel(Parcel source) {
			return new ParcelData(source);
		}

		@Override
		public ParcelData[] newArray(int size) {
			return new ParcelData[size];
		}

	};

}
