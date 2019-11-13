package com.example.busapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataScrape {
    private Agency agency;
    private int agencyID;
    private URLRequest agencyReq;
    private URLRequest stopReq;

    public DataScrape(int agencyID) {
        this.agencyID = agencyID;
        agencyReq = new URLRequest("https://feeds.transloc.com/3/" +
                "agencies?agencies=" + agencyID);
        stopReq = new URLRequest("https://feeds.transloc.com/3/" +
                "stops?include_routes=true&agencies=" + agencyID);
    }

    public boolean initAgency() {
        String json = agencyReq.get();
        try {
            JSONObject jsonBody = new JSONObject(json);
            JSONObject agencyInfo = jsonBody.getJSONArray("agencies")
                    .getJSONObject(0);
            int id = (int) agencyInfo.get("id");
            String location = (String) agencyInfo.get("location");
            String name = (String) agencyInfo.get("name");

            agency = new Agency(id, location, name);

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public boolean initStops() {
        String json = stopReq.get();
        try {
            JSONObject jsonBody = new JSONObject(json);
            JSONArray stops = jsonBody.getJSONArray("stops");

            for (int i = 0; i < stops.length(); ++i) {
                JSONObject stopObj = stops.getJSONObject(i);

                String code = stopObj.getString("code");
                int id = stopObj.getInt("id");
                String name = stopObj.getString("name");
                JSONArray position = stopObj.getJSONArray("position");
                double latitude = position.getDouble(0);
                double longitude = position.getDouble(1);

                Stop stop = new Stop(code, id, name, latitude, longitude);
                agency.addStop(stop);
            }

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public Agency getAgency() {
        return agency;
    }
}
