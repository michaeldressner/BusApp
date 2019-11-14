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
    private URLRequest routeReq;

    public DataScrape(int agencyID) {
        this.agencyID = agencyID;
        agencyReq = new URLRequest("https://feeds.transloc.com/3/" +
                "agencies?agencies=" + agencyID);
        stopReq = new URLRequest("https://feeds.transloc.com/3/" +
                "stops?include_routes=true&agencies=" + agencyID);
        routeReq = new URLRequest("https://feeds.transloc.com/3/" +
                "routes?agencies=" + agencyID);

        initAgency();
        initStopsAndRoutes();
        getRouteData();
    }

    private boolean initAgency() {
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

    private boolean initStopsAndRoutes() {
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
                String description = stopObj.getString("description");
                String locationType = stopObj.getString("location_type");
                Object parentStation = stopObj.get("parent_station_id");
                int parentStationID = (stopObj.isNull("parent_station_id")) ? 0 : (int) parentStation;

                Stop stop = new Stop(code, id, name, latitude, longitude);
                stop.setDescription(description);
                stop.setLocationType(locationType);
                stop.setParentStationID(parentStationID);

                agency.addStop(stop);
            }

            // Init route info as well
            JSONArray routes = jsonBody.getJSONArray("routes");

            for (int i = 0; i < routes.length(); ++i) {
                JSONObject routeObj = routes.getJSONObject(i);

                int id = routeObj.getInt("id");
                Route route = new Route(id);

                JSONArray stopList = routeObj.getJSONArray("stops");

                for (int j = 0; j < stopList.length(); ++j) {
                    int stopID = stopList.getInt(j);
                    Stop stop = agency.getStopByID(stopID);
                    route.addStop(stop);
                }

                agency.addRoute(route);
            }

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    private boolean getRouteData() {
        String json = routeReq.get();

        try {
            JSONObject jsonBody = new JSONObject(json);
            JSONArray routes = jsonBody.getJSONArray("routes");

            for (int i = 0; i < routes.length(); ++i) {
                JSONObject route = routes.getJSONObject(i);

                int id = route.getInt("id");
                Route r = agency.getRouteByID(id);

                boolean active = route.getBoolean("is_active");
                r.setActive(active);

                String longName = route.getString("long_name");
                r.setLongName(longName);
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
