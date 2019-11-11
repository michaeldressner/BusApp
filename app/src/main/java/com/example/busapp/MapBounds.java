package com.example.busapp;

class MapBounds {
    private double topLeftLat;
    private double topLeftLong;
    private double bottomRightLat;
    private double bottomRightLong;

    public MapBounds(double topLeftLat, double topLeftLong,
                     double bottomRightLat, double bottomRightLong) {
        this.topLeftLat = topLeftLat;
        this.topLeftLong = topLeftLong;
        this.bottomRightLat = bottomRightLat;
        this.bottomRightLong = bottomRightLong;
    }

    public double[] getTopLeft() {
        double[] result = {topLeftLat, topLeftLong};

        return result;
    }

    public double[] getBottomRight() {
        double[] result = {bottomRightLat, bottomRightLong};

        return result;
    }
}
