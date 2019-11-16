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

            boolean hasNotifications =
                    agencyInfo.getBoolean("has_notifications");
            agency.setNotifications(hasNotifications);
            boolean hasSchedules =
                    agencyInfo.getBoolean("has_schedules");
            agency.setSchedules(hasSchedules);
            boolean hasTripPlanning =
                    agencyInfo.getBoolean("has_trip_planning");
            agency.setTripPlanning(hasTripPlanning);
            String color = agencyInfo.getString("color");
            agency.setColor(color);
            String longName = agencyInfo.getString("long_name");
            agency.setLongName(longName);
            if (!agencyInfo.isNull("position")) {
                JSONArray position = agencyInfo.getJSONArray("position");
                double lat = position.getDouble(0);
                double lon = position.getDouble(1);
                agency.setPosition(new Position(lat, lon));
            }
            String shortName = agencyInfo.getString("short_name");
            agency.setShortName(shortName);
            String textColor = agencyInfo.getString("text_color");
            agency.setTextColor(textColor);
            String timeZone = agencyInfo.getString("timezone");
            agency.setTimeZone(timeZone);
            int timeZoneOffset = agencyInfo.getInt("timezone_offset");
            agency.setTimeZoneOffset(timeZoneOffset);
            String url = agencyInfo.getString("url");
            agency.setURL(url);

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

                Stop stop = new Stop(code, id, name, latitude, longitude);
                String description = stopObj.getString("description");
                stop.setDescription(description);
                String locationType = stopObj.getString("location_type");
                stop.setLocationType(locationType);
                Object parentStation = stopObj.get("parent_station_id");
                int parentStationID = (stopObj.isNull("parent_station_id"))
                        ? 0 : (int) parentStation;
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

                if (!route.isNull("bounds")) {
                    JSONArray bounds = route.getJSONArray("bounds");

                    double tlLat = bounds.getDouble(0);
                    double tlLong = bounds.getDouble(1);
                    double brLat = bounds.getDouble(2);
                    double brLong = bounds.getDouble(3);

                    r.setBounds(new MapBounds(tlLat, tlLong, brLat, brLong));
                }

                boolean active = route.getBoolean("is_active");
                r.setActive(active);
                String longName = route.getString("long_name");
                r.setLongName(longName);
                String shortName = route.getString("short_name");
                r.setShortName(shortName);
                String type = route.getString("type");
                r.setType(type);
                String color = route.getString("color");
                r.setColor(color);
                String textColor = route.getString("text_color");
                r.setTextColor(textColor);
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
