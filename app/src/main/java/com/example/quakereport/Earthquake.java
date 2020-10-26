package com.example.quakereport;

import android.widget.ArrayAdapter;

public class Earthquake {
    private double magnitude;
    private String location;
    private  long date;
    private String url;
    public Earthquake(double magnitude,String location,long date,String url)
    {
        this.date=date;
        this.location=location;
        this.magnitude=magnitude;
        this.url=url;
    }
    public double getMagnitude(){
        return magnitude;
    }
    public String getLocation(){
        return location;
    }
    public long getDate(){
        return date;
    }
    public String getUrl(){
        return url;
    }
}
