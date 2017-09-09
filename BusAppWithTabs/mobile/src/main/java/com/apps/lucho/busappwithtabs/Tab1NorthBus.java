package com.apps.lucho.busappwithtabs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class Tab1NorthBus extends Fragment {

    private static final String TAG = Tab1NorthBus.class.getSimpleName();
    final String API_KEY = "620142b9-cbfb-4924-8f34-e048760bd20a";
    ArrayList<Stop> stopList = new ArrayList<>();
    ArrayList<Bus> busList = new ArrayList<>();
    ArrayList<RowData> rowDataList = new ArrayList<>();
    //String busEnteredStr = "Q58";
    String busEnteredStr;

    private RecyclerView mRecyclerView;
    static public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_north_bus, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.busRecyclerViewNorth);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        busEnteredStr = getArguments().getString("busCode");

        Log.d(TAG, "TAB 1: " + busEnteredStr);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://bustime.mta.info/api/stops-on-route-for-direction?&routeId=MTA+NYCT_" + busEnteredStr + "&directionId=0", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String stopsString = new String(response);
                populateStops(stopsString, busEnteredStr);

                AsyncHttpClient RTclient = new AsyncHttpClient();
                RTclient.get("http://api.prod.obanyc.com/api/siri/vehicle-monitoring.json?key=" + API_KEY + "&LineRef=MTA%20NYCT_" + busEnteredStr + "&DirectionRef=0&VehicleMonitoringDetailLevel=minimum", new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        // called before request is started
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        String vehicleMonitoringStr = new String(response);
                        try{
                            JSONObject jsonObjVehicleMon = new JSONObject(vehicleMonitoringStr);
                            int numOfBuses = jsonObjVehicleMon.getJSONObject("Siri").getJSONObject("ServiceDelivery").getJSONArray("VehicleMonitoringDelivery").getJSONObject(0).getJSONArray("VehicleActivity").length();
                            populateBuses(vehicleMonitoringStr, numOfBuses);

                            populateRowData(stopList, busList);

                            mAdapter = new BusStopAdapter(rowDataList);
                            mRecyclerView.setAdapter(mAdapter);

                        }catch (JSONException jse){
                            Log.d(TAG,jse.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.d(TAG,"ERROR FAILED GET: " + e.getMessage());
                    }
                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d(TAG,"ERROR FAILED GET: " + e.getMessage());
            }
            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    public void populateStops(String jsonString, String bus){
        try {
            JSONObject jsonObj = new JSONObject(jsonString);
            stopList.clear();
            for (int i = 0; i < jsonObj.getJSONArray("stops").length(); i++) {
                Stop myStop = new Stop(jsonObj.getJSONArray("stops").getJSONObject(i).get("id").toString(),
                                       jsonObj.getJSONArray("stops").getJSONObject(i).getDouble("latitude"),
                                       jsonObj.getJSONArray("stops").getJSONObject(i).getDouble("longitude"),
                                       jsonObj.getJSONArray("stops").getJSONObject(i).get("name").toString(),
                                       jsonObj.getJSONArray("stops").getJSONObject(i).get("stopDirection").toString());
                stopList.add(myStop);
                //Log.d(TAG, myStop.toString());
            }
        }catch (JSONException jse){
            Log.d(TAG, jse.getMessage());
        }
    }

    public void populateBuses(String jsoBusesStr, int numBuses){
        try {
            JSONObject jsoBuses = new JSONObject(jsoBusesStr);
            busList.clear();
            for (int i = 0; i < numBuses; i++) {
                Bus myBus = new Bus(jsoBuses.getJSONObject("Siri").getJSONObject("ServiceDelivery").getJSONArray("VehicleMonitoringDelivery").getJSONObject(0).getJSONArray("VehicleActivity").getJSONObject(i).getJSONObject("MonitoredVehicleJourney").getString("LineRef"),
                                    jsoBuses.getJSONObject("Siri").getJSONObject("ServiceDelivery").getJSONArray("VehicleMonitoringDelivery").getJSONObject(0).getJSONArray("VehicleActivity").getJSONObject(i).getJSONObject("MonitoredVehicleJourney").getJSONObject("VehicleLocation").getDouble("Longitude"),
                                    jsoBuses.getJSONObject("Siri").getJSONObject("ServiceDelivery").getJSONArray("VehicleMonitoringDelivery").getJSONObject(0).getJSONArray("VehicleActivity").getJSONObject(i).getJSONObject("MonitoredVehicleJourney").getJSONObject("VehicleLocation").getDouble("Latitude"),
                                    jsoBuses.getJSONObject("Siri").getJSONObject("ServiceDelivery").getJSONArray("VehicleMonitoringDelivery").getJSONObject(0).getJSONArray("VehicleActivity").getJSONObject(i).getJSONObject("MonitoredVehicleJourney").getJSONObject("MonitoredCall").getJSONObject("Extensions").getJSONObject("Distances").getString("PresentableDistance"),
                                    jsoBuses.getJSONObject("Siri").getJSONObject("ServiceDelivery").getJSONArray("VehicleMonitoringDelivery").getJSONObject(0).getJSONArray("VehicleActivity").getJSONObject(i).getJSONObject("MonitoredVehicleJourney").getJSONObject("MonitoredCall").getString("StopPointRef"),
                                    jsoBuses.getJSONObject("Siri").getJSONObject("ServiceDelivery").getJSONArray("VehicleMonitoringDelivery").getJSONObject(0).getJSONArray("VehicleActivity").getJSONObject(i).getJSONObject("MonitoredVehicleJourney").getJSONObject("MonitoredCall").getString("StopPointName"),
                                    jsoBuses.getJSONObject("Siri").getJSONObject("ServiceDelivery").getJSONArray("VehicleMonitoringDelivery").getJSONObject(0).getJSONArray("VehicleActivity").getJSONObject(i).getJSONObject("MonitoredVehicleJourney").getString("DestinationName"));
                //Log.d(TAG,myBus.toString());
                busList.add(myBus);
            }

            // change name of tab
            TabLayout tl = (TabLayout) getActivity().findViewById(R.id.tabs);
            tl.getTabAt(0).setText("TO " + busList.get(0).getDestinationName());


        }catch(JSONException jse){
            Log.d(TAG, jse.getMessage());
        }
    }

    public void populateRowData(ArrayList<Stop> ALstop, ArrayList<Bus> ALbus){
        String stopName;
        String busPosition;
        String otherBus = "";
        boolean isOrCloseToStop;
        rowDataList.clear();
        for (int i = 0; i < ALstop.size(); i++) {
            stopName = ALstop.get(i).getStopName();
            isOrCloseToStop = false;
            busPosition="";
            otherBus = "";
//            for (int j = 0; j < ALbus.size(); j++) {
//                if(stopName.equals(ALbus.get(j).getStopPointName())){
//                    isOrCloseToStop = true;
//                    busPosition = ALbus.get(j).getPresentableDistance();
//                }
//            }
            for (int j = 0; j < ALbus.size(); j++) {
                if(stopName.equals(ALbus.get(j).getStopPointName())){
                    if(isOrCloseToStop==false) { // for closest bus
                        isOrCloseToStop = true;
                        //busPosition = ALbus.get(j).isLimited() + " " + ALbus.get(j).getPresentableDistance();
                        busPosition = ALbus.get(j).getPresentableDistance();
                    }
                    else {
                        //otherBus += ALbus.get(j).isLimited() + " " + ALbus.get(j).getPresentableDistance() + "\n";
                        otherBus += ALbus.get(j).getPresentableDistance() + "\n"; // for other nearby buses
                    }
                }
            }
            RowData rd = new RowData(stopName, busPosition, otherBus, isOrCloseToStop);
            rowDataList.add(rd);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.d(TAG, "TAB 1 VISIBLE");
        //mAdapter.notifyDataSetChanged();
    }
}