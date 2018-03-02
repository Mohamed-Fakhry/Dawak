package com.example.dawak.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    private double lat;
    private double lang;

    public Location() {}

    public Location(double lat, double lang) {
        this.lat = lat;
        this.lang = lang;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lang);
    }

    protected Location(Parcel in) {
        this.lat = in.readDouble();
        this.lang = in.readDouble();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
