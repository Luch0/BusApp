package com.apps.lucho.busappwithtabs;

public class Bus {

    protected String lineRef;
    protected double longitude;
    protected double latitude;
    protected String presentableDistance;
    protected String stopPointRef;
    protected String stopPointName;
    protected String destinationName;
    protected boolean limited;


    public Bus(String lineRef, Double longitude, Double latitude, String presentableDistance, String stopPointRef, String stopPointName, String destinationName){
        this.lineRef = lineRef;
        this.longitude = longitude;
        this.latitude = latitude;
        this.presentableDistance = presentableDistance;
        this.stopPointRef = stopPointRef;
        this.stopPointName = stopPointName;
        this.destinationName = destinationName;
        if(this.destinationName.startsWith("LTD")) limited = true;
        else limited = false;
    }

    public String toString(){
        return this.lineRef + " " + this.longitude + " " + this.latitude + " " + this.presentableDistance + " " + this.stopPointRef + " " + this.stopPointName + " " + this.destinationName + " " + this.limited + "\n";
    }

    public String getLineRef(){
        return this.lineRef;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public String getPresentableDistance(){
        return this.presentableDistance;
    }
    public String getStopPointRef(){
        return this.stopPointRef;
    }
    public  String getStopPointName(){
        return this.stopPointName;
    }
    public String getDestinationName(){
        return this.destinationName;
    }
    public boolean isLimited(){
        return limited;
    }



}
