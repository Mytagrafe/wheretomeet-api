package com.wheretomeet.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.javatuples.Pair;

public class DistanceDuration implements Serializable {

    private final static int MILES_METERS_RATIO = 1609;

    public enum TravelMethod {
        DRIVING, 
        WALKING;
    }

    private String userId;
    private String placeId;
    private TravelMethod travelMethod;
    private ArrayList<Pair<String, String>> distanceDurations;

    public DistanceDuration() {
        // default constructor
    }

    public DistanceDuration(String userId, String distance, String duration) {
        this.userId = userId;
        this.distanceDurations = new ArrayList<Pair<String, String>>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public ArrayList<Pair<String, String>> getDistanceDurations() {
        return distanceDurations;
    }

    public void addDistanceDuration(String distance, String duration, TravelMethod travelMethod) {
        int ordinalEnumValue = travelMethod.ordinal();
        Pair<String, String> distDurationPair = new Pair<>(distance, duration);
        this.distanceDurations.add(ordinalEnumValue, distDurationPair);
    }

    public Pair<String, String> getDistanceDuration(TravelMethod travelMethod) {
        int ordinalEnumValue = travelMethod.ordinal();
        return this.distanceDurations.get(ordinalEnumValue);
    }

    public TravelMethod getTravelMethod() {
        return travelMethod;
    }

    public void setTravelMethod(TravelMethod travelMethod) {
        this.travelMethod = travelMethod;
    }

    //utility functions
    public static String distanceInMiles(float distanceInMeters) {
        float distanceInMiles = (float)(distanceInMeters/MILES_METERS_RATIO);
        return String.valueOf(distanceInMiles);
    }

    public static String distanceInMeters(float distanceInMiles) {
        float distanceInMeters = (float)(distanceInMiles * MILES_METERS_RATIO);
        return String.valueOf(distanceInMeters);
    }
}
