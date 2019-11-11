package com.example.busapp;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Route {
    private MapBounds bounds;
    private String color;
    private String description;
    private Integer id;
    private boolean active;
    private String longName;
    private String shortName;
    private String textColor;
    private String type;
    private String url;
    private ArrayList<Stop> stops;

    private Route() {
    }

    public Route(MapBounds bounds, String description, int id,
                 String longName, String shortName, String url) {
        this.bounds = bounds;
        this.description = description;
        this.id = id;
        this.longName = longName;
        this.shortName = shortName;
        this.url = url;
    }

    public void addStops(ArrayList<Stop> stops) {
        for (Stop stop : stops) {
            this.stops.add(stop);
        }
    }

    public ArrayList<Stop> getStops() {
        ArrayList<Stop> result = new ArrayList<>();

        for (Stop stop : stops) {
            result.add(stop);
        }

        return result;
    }

    public int getID() {
        return id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Route) {
            Route route = (Route) obj;

            return (this.id == route.id);
        }

        return super.equals(obj);
    }
}
