package com.wheretomeet.model;

import java.util.HashMap;

import org.javatuples.Pair;

public class Venue {

    enum TravelMethod {
        DRIVING, 
        WALKING
    }

    private float latitude;
    private float longitude;
    private String venueName;
    private String venueAddress;
    private String venuePhoneNumber;
    private String venueId;
    private HashMap<String, Pair<String, String>[]> distanceDuration;

    public Venue() {
        //default constructor
    }

    public Venue(long lat, long lng, String name, String address, String phoneNumber, String venueId) {
        this.latitude = lat;
        this.longitude = lng;
        this.venueName = name;
        this.venueAddress = address;
        this.venuePhoneNumber = phoneNumber;
        this.venueId = venueId;
        this.distanceDuration = new HashMap<>();
    }

    public void setVenueCoordinates(float lat, float lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    public float[] getVenueCoordinates() {
        return new float[] {latitude, longitude};
    }

    public void setVenueName(String name) {
        this.venueName = name;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String address) {
        this.venueAddress = address;
    }

    public String getVenuePhoneNumber() {
        return venuePhoneNumber;
    }

    public void setVenuePhoneNumber(String phoneNumber) {
        this.venuePhoneNumber = phoneNumber;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public void storeUserDistanceDurationToVenue(String uid, Pair<String, String> pair, TravelMethod method) {
        try {
            if(method == TravelMethod.DRIVING) {
                distanceDuration.get(uid)[0] = pair;
            }

            else if(method == TravelMethod.WALKING) {
                distanceDuration.get(uid)[1] = pair;
            }
        }
        catch(NullPointerException e) {
            //do nothing for now...
        }
    }

    public Pair<String, String> getUserDistanceDurationToVenue(String uid, TravelMethod method) {
        try {
            if(method == TravelMethod.DRIVING) {
                return distanceDuration.get(uid)[0];
            }

            else if(method == TravelMethod.WALKING) {
                return distanceDuration.get(uid)[1];
            }
        }
        catch(NullPointerException e) {
            //do nothing for now...
        }
        return null;
    }
}
