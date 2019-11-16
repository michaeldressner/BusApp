package com.example.busapp;

import androidx.annotation.Nullable;

public class Stop {
    private String code;
    private String description;
    private int id;
    private String locationType;
    private String name;
    private Integer parentStationID;
    private Position position;
    private String url;

    private Stop() {
    }

    public Stop(String code, int id, String name, double latitude,
                double longitude) {
        this.code = code;
        this.id = id;
        this.name = name;
        this.position = new Position(latitude, longitude);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getID() {
        return id;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getName() { return name; }

    public Integer getParentStationID() {
        return parentStationID;
    }

    public void setParentStationID(Integer parentStationID) {
        this.parentStationID = parentStationID;
    }

    public Position getPosition() { return position; }

    public String getURL() {
        return url;
    }

    public void setURL(String url) { this.url = url; }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Stop) {
            Stop stop = (Stop) obj;

            return (this.id == stop.id);
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        String result = "Stop: id=" + id + ",code=" + code + ",name=" + name +
                ",position=(" + position.getLatitude() + "," +
                position.getLongitude() + ")";
        return result;
    }
}