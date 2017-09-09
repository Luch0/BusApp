package com.apps.lucho.busappwithtabs;


public class RowData {

    String stopName;
    String busPosition;
    String OtherBus;
    boolean isOrCloseToStop;

    public RowData(String inStopName, String inBusPosition, String inOtherBus, boolean inIsOrCloseToStop){
        stopName = inStopName;
        busPosition = inBusPosition;
        OtherBus = inOtherBus;
        isOrCloseToStop = inIsOrCloseToStop;
    }

    public String getStopName() {
        return stopName;
    }

    public String getBusPosition() {
        return busPosition;
    }

    public String getOtherBus() {
        return OtherBus;
    }

    public boolean isOrCloseToStop() {
        return isOrCloseToStop;
    }
}
