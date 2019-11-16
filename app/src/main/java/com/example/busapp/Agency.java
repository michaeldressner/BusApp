package com.example.busapp;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Agency {
    private boolean arrivalPredictions;
    private MapBounds bounds;
    private String color;
    private boolean hasNotifications;
    private boolean hasSchedules;
    private boolean hasTripPlanning;
    private int id;
    private String location;
    private String longName;
    private String name;
    private Position position;
    private String shortName;
    private String textColor;
    private String timeZone;
    private Integer timeZoneOffset;
    private String url;

    private ArrayList<Stop> stops;
    private ArrayList<Route> routes;
    private ArrayList<Vehicle> vehicles;

    public Agency(int id, String location, String name) {
        this.id = id;
        this.location = location;
        this.name = name;

        this.stops = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    public void addStop(Stop stop) {
        stops.add(stop);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void addStopToRoute(Route route, Stop stop) {
        route.addStop(stop);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public Stop getStopByID(int stopID) {
        // Create a fake stop with ID = stopID. Only this field matters in the
        // equals method anyways.

        Stop search = new Stop("", stopID, "", 0, 0);

        int idx = stops.indexOf(search);

        if (idx < 0) return null;
        else return stops.get(idx);
    }

    public Route getRouteByID(int routeID) {
        // Create a fake route with ID = routeID. Only this field matters in the
        // equals method anyways.

        Route search = new Route(routeID);

        int idx = routes.indexOf(search);

        if (idx < 0) return null;
        else return routes.get(idx);
    }

    public int getID() { return id; }

    public String getLocation() { return location; }

    public String getName() { return name; }

    public ArrayList<Stop> getStops() { return stops; }

    public boolean arrivalPredictions() { return arrivalPredictions; }

    public void setArrivalPredictions(boolean arrivalPredictions) {
        this.arrivalPredictions = arrivalPredictions;
    }

    public MapBounds getBounds() { return bounds; }

    public void setBounds(MapBounds bounds) { this.bounds = bounds; }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public boolean hasNotifications() { return hasNotifications; }

    public void setNotifications(boolean hasNotifications) {
        this.hasNotifications = hasNotifications;
    }

    public boolean hasSchedules() { return hasSchedules; }

    public void setSchedules(boolean hasSchedules) {
        this.hasSchedules = hasSchedules;
    }

    public boolean hasTripPlanning() { return hasTripPlanning; }

    public void setTripPlanning(boolean hasTripPlanning) {
        this.hasTripPlanning = hasTripPlanning;
    }

    public String getLongName() { return longName; }

    public void setLongName(String longName) { this.longName = longName; }

    public Position getPosition() { return position; }

    public void setPosition(Position position) { this.position = position; }

    public String getShortName() { return shortName; }

    public void setShortName(String shortName) { this.shortName = shortName; }

    public String getTextColor() { return textColor; }

    public void setTextColor(String textColor) { this.textColor = textColor; }

    public String getTimeZone() { return timeZone; }

    public void setTimeZone(String timeZone) { this.timeZone = timeZone; }

    public Integer getTimeZoneOffset() { return timeZoneOffset; }

    public void setTimeZoneOffset(Integer timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public String getURL() { return url; }

    public void setURL(String url) { this.url = url; }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Agency) {
            Agency agency = (Agency) obj;

            return (this.id == agency.id);
        }

        return super.equals(obj);
    }
}
