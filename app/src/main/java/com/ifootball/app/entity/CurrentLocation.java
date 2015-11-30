package com.ifootball.app.entity;

import java.io.Serializable;

import com.neweggcn.lib.json.annotations.SerializedName;

public class CurrentLocation implements Serializable {
    private static final long serialVersionUID = 5826645686838694309L;
    @SerializedName("latitude")
    private float latitude;// 纬度
    @SerializedName("longitude")
    private float longitude;// 经度
    @SerializedName("listtype")
    private int listtype;
    @SerializedName("pageindex")
    private int pageindex;
    @SerializedName("pagesize")
    private int pagesize;

    public CurrentLocation() {
        super();
    }

    public CurrentLocation(float latitude, float longitude, int listtype,
                           int pageindex, int pagesize) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.listtype = listtype;
        this.pageindex = pageindex;
        this.pagesize = pagesize;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getListtype() {
        return listtype;
    }

    public void setListtype(int listtype) {
        this.listtype = listtype;
    }

    public int getPageindex() {
        return pageindex;
    }

    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
