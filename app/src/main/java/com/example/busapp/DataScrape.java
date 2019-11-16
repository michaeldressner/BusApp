package com.example.busapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataScrape {
    private Agency agency;
    private int agencyID;
    private URLRequest agencyReq;
    private URLRequest stopReq;
    private URLRequest routeReq;
    private URLRequest segmentReq;
    private URLRequest vehicleReq;
    private URLRequest announcementReq;
    private URLRequest arrivalReq;

    public DataScrape(int agencyID) {
        this.agencyID = agencyID;
        agencyReq = new URLRequest("https://feeds.transloc.com/3/" +
                "agencies?agencies=" + agencyID);
        stopReq = new URLRequest("https://feeds.transloc.com/3/" +
                "stops?include_routes=true&agencies=" + agencyID);
        routeReq = new URLRequest("https://feeds.transloc.com/3/" +
                "routes?agencies=" + agencyID);
        vehicleReq = new URLRequest("https://feeds.transloc.com/3/" +
                "vehicle_statuses?agencies=" + agencyID);

        initAgency();
        initStopsAndRoutes();
        getRouteInfo();
        getVehicleInfo();
    }

    private boolean initAgency() {
        String json = agencyReq.get();
        try {
            JSONObject jsonBody = new JSONObject(json);
            JSONObject agencyInfo = jsonBody.getJSONArray("agencies")
                    .getJSONObject(0);
            int id = agencyInfo.getInt("id");
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
            Integer timeZoneOffset =
                    extract(Integer.class, agencyInfo, "timezone_offset");
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
                Integer parentStationID =
                        extract(Integer.class, stopObj, "parent_station_id");
                stop.setParentStationID(parentStationID);
                String url = stopObj.getString("url");
                stop.setURL(url);

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

    private boolean getRouteInfo() {
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
                String description = route.getString("description");
                r.setDescription(description);
                String url = route.getString("url");
                r.setURL(url);
            }

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    private boolean getVehicleInfo() {
        String json = vehicleReq.get();

        try {
            JSONObject jsonBody = new JSONObject(json);

            JSONArray vehicles = jsonBody.getJSONArray("vehicles");

            for (int i = 0; i < vehicles.length(); ++i) {
                JSONObject vehicle = vehicles.getJSONObject(i);

                int id = vehicle.getInt("id");
                String serviceStatus = vehicle.getString("service_status");
                int routeID = vehicle.getInt("route_id");
                Route route = agency.getRouteByID(routeID);
                Vehicle v = new Vehicle(id, serviceStatus, route);

                Integer numCars =
                        extract(Integer.class, vehicle, "num_cars");
                v.setNumCars(numCars);
                String callName = vehicle.getString("call_name");
                v.setCallName(callName);
                Integer currentStopID =
                        extract(Integer.class, vehicle, "current_stop_id");
                if (currentStopID != null) {
                    Stop currentStop = agency.getStopByID(currentStopID);
                    v.setCurrentStop(currentStop);
                }
                Integer nextStopID =
                        extract(Integer.class, vehicle, "next_stop");
                if (nextStopID != null) {
                    Stop nextStop = agency.getStopByID(nextStopID);
                    v.setNextStop(nextStop);
                }
                String arrivalStatus = vehicle.getString("arrival_status");
                v.setArrivalStatus(arrivalStatus);
                if (!vehicle.isNull("position")) {
                    JSONArray posArray = vehicle.getJSONArray("position");
                    double lat = posArray.getDouble(0);
                    double lon = posArray.getDouble(1);
                    v.setPosition(new Position(lat, lon));
                }
                Integer heading =
                        extract(Integer.class, vehicle, "heading");
                v.setHeading(heading);
                Double speed =
                        extract(Double.class, vehicle, "speed");
                v.setSpeed(speed);
                // TODO segment_id
                boolean offRoute = vehicle.getBoolean("off_route");
                v.setOffRoute(offRoute);
                Long timestamp =
                        extract(Long.class, vehicle, "timestamp");
                v.setTimestamp(timestamp);
                Integer load =
                        extract(Integer.class, vehicle, "load");
                v.setLoad(load);
            }

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    private <T> T extract(Class<T> type, JSONObject jo, String field)
            throws JSONException {
        if (!jo.isNull(field)) {
            Object result = jo.get(field);

            if (type.isInstance(result))
                return (T) result;
        }

        return null;
    }

    public Agency getAgency() {
        return agency;
    }
}
