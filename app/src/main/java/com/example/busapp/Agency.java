package com.example.busapp;

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
    private int timeZoneOffset;
    private String url;

    private ArrayList<Stop> stops;
    private ArrayList<Route> routes;
    private ArrayList<Vehicle> vehicles;

    public Agency(int id, String location, String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public void addStop(Stop stop) {
        stops.add(stop);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Agency) {
            Agency agency = (Agency) obj;

            return (this.id == agency.id);
        }

        return super.equals(obj);
    }
}
