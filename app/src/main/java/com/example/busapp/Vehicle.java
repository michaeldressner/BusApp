package com.example.busapp;

import androidx.annotation.Nullable;

public class Vehicle {
    private Integer id;
    private Integer numCars;
    private String serviceStatus;
    private Route route;
    private String callName;
    private Stop currentStop;
    private Stop nextStop;
    private String arrivalStatus;
    private Integer heading;
    private double speed;
    private Integer segmentID;
    private boolean offRoute;
    private Integer timestamp;
    private String apcStatus;

    public Vehicle(int id) {
        this.id = id;
    }

    public void setRoute(Route r) {
        this.route = r;
    }

    public Route getRoute() {
        return route;
    }

    public int getID() { return id; }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) obj;

            return (this.id == vehicle.id);
        }

        return super.equals(obj);
    }
}
