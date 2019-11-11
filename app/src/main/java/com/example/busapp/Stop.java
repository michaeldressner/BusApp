package com.example.busapp;

import androidx.annotation.Nullable;

public class Stop {
    private String code;
    private String description;
    private Integer id;
    private String locationType;
    private String name;
    private Integer parentStationID;
    private Position position;
    private String url;

    private Stop() {
    }

    public Stop(String code, String description, int id, String locationType,
                String name, int parentStationID, double latitude,
                double longitude, String url) {
        this.code = code;
        this.description = description;
        this.id = id;
        this.locationType = locationType;
        this.name = name;
        this.parentStationID = parentStationID;
        this.position = new Position(latitude, longitude);
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getID() {
        return id;
    }

    public String getLocationType() {
        return locationType;
    }

    public String getName() {
        return name;
    }

    public int getParentStationID() {
        return parentStationID;
    }

    public Position getPosition() { return position; }

    public String getURL() {
        return url;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Stop) {
            Stop stop = (Stop) obj;

            return (this.id == stop.id);
        }

        return super.equals(obj);
    }
}