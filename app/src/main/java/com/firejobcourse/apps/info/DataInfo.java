package com.firejobcourse.apps.info;

import android.os.Parcel;
import android.os.Parcelable;

public class DataInfo implements Parcelable {
    private String judul_info, link_img, jadwal, deskripsi;

    public DataInfo() {
    }

    public DataInfo(String judul_info, String link_img, String jadwal, String deskripsi) {
        this.judul_info = judul_info;
        this.link_img = link_img;
        this.jadwal = jadwal;
        this.deskripsi = deskripsi;
    }

    public String getJudul_info() {
        return judul_info;
    }

    public void setJudul_info(String judul_info) {
        this.judul_info = judul_info;
    }

    public String getLink_img() {
        return link_img;
    }

    public void setLink_img(String link_img) {
        this.link_img = link_img;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul_info);
        dest.writeString(this.link_img);
        dest.writeString(this.jadwal);
        dest.writeString(this.deskripsi);
    }

    public void readFromParcel(Parcel source) {
        this.judul_info = source.readString();
        this.link_img = source.readString();
        this.jadwal = source.readString();
        this.deskripsi = source.readString();
    }

    protected DataInfo(Parcel in) {
        this.judul_info = in.readString();
        this.link_img = in.readString();
        this.jadwal = in.readString();
        this.deskripsi = in.readString();
    }

    public static final Parcelable.Creator<DataInfo> CREATOR = new Parcelable.Creator<DataInfo>() {
        @Override
        public DataInfo createFromParcel(Parcel source) {
            return new DataInfo(source);
        }

        @Override
        public DataInfo[] newArray(int size) {
            return new DataInfo[size];
        }
    };
}
