package com.example.busapp;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Route {
    private MapBounds bounds;
    private String color;
    private String description;
    private int id;
    private boolean active;
    private String longName;
    private String shortName;
    private String textColor;
    private String type;
    private String url;
    private ArrayList<Stop> stops;

    private Route() {
    }

    public Route(int id) {
        this.id = id;

        stops = new ArrayList<>();
    }

    public int getID() {
        return id;
    }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public String getLongName() { return longName; }

    public void setLongName(String longName) { this.longName = longName; }

    public String getTextColor() { return textColor; }

    public void setTextColor(String textColor) { this.textColor = textColor; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getURL() { return url; }

    public void setURL(String url) { this.url = url; }

    public MapBounds getBounds() { return bounds; }

    public void setBounds(MapBounds bounds) { this.bounds = bounds; }

    public String getShortName() { return shortName; }

    public void setShortName(String shortName) { this.shortName = shortName; }

    public void addStops(ArrayList<Stop> stops) {
        for (Stop stop : stops) {
            this.stops.add(stop);
        }
    }

    public void addStop(Stop stop) {
        this.stops.add(stop);
    }

    public ArrayList<Stop> getStops() {
        ArrayList<Stop> result = new ArrayList<>();

        for (Stop stop : stops) {
            result.add(stop);
        }

        return result;
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
