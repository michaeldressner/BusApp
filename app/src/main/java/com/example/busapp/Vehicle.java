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
    private Position position;
    private Integer heading;
    private Double speed;
    private Integer load;
    private Integer segmentID;
    private boolean offRoute;
    private Long timestamp;
    private String apcStatus;

    public Vehicle(int id, String serviceStatus, Route route) {
        this.id = id;
        this.serviceStatus = serviceStatus;
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public int getID() { return id; }

    public String getServiceStatus() { return serviceStatus; }

    public Integer getNumCars() { return numCars; }

    public void setNumCars(Integer numCars) { this.numCars = numCars; }

    public String getCallName() { return callName; }

    public void setCallName(String callName) { this.callName = callName; }

    public Stop getCurrentStop() { return currentStop; }

    public void setCurrentStop(Stop currentStop) {
        this.currentStop = currentStop;
    }

    public Stop getNextStop() { return nextStop; }

    public void setNextStop(Stop nextStop) { this.nextStop = nextStop; }

    public String getArrivalStatus() { return arrivalStatus; }

    public void setArrivalStatus(String arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

    public int getHeading() { return heading; }

    public void setHeading(int heading) { this.heading = heading; }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) { this.speed = speed; }

    public Integer getSegmentID() { return segmentID; }

    public void setSegmentID(Integer segmentID) { this.segmentID = segmentID; }

    public boolean isOffRoute() { return offRoute; }

    public void setOffRoute(boolean offRoute) { this.offRoute = offRoute; }

    public Long getTimestamp() { return timestamp; }

    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public String getApcStatus() { return apcStatus; }

    public void setApcStatus(String apcStatus) { this.apcStatus = apcStatus; }

    public Position getPosition() { return position; }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getLoad() { return load; }

    public void setLoad(Integer load) { this.load = load; }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) obj;

            return (this.id == vehicle.id);
        }

        return super.equals(obj);
    }
}
