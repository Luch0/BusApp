package com.apps.lucho.busappwithtabs;


public class Stop {

    private String stopID;
    private double latitude;
    private double longitude;
    private String stopName;
    private String stopDirection;

    public Stop(String stopID, double latitude, double longitude, String stopName, String stopDirection){
        this.stopID = stopID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stopName = stopName;
        this.stopDirection = stopDirection;
    }

    public String toString(){
        return this.stopID + " " + this.latitude + " " + this.longitude + " " + this.stopName + " " +  this.stopDirection +"\n";
    }

    public String getStopID() {
        return this.stopID;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public String getStopName() {
        return this.stopName;
    }
    public String getStopDirection() {
        return this.stopDirection;
    }

}
