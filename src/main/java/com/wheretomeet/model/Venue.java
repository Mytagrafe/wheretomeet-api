package com.wheretomeet.model;

import java.io.Serializable;
import java.util.HashMap;

import com.wheretomeet.model.DistanceDuration.TravelMethod;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.javatuples.Pair;

public class Venue implements Serializable{
    final static Logger log = LoggerFactory.getLogger(Venue.class);

    private float[] venueCoordinates = new float[2];
    private String venueName;
    private String venueAddress;
    private String venuePhoneNumber;
    private String venueId;
    private HashMap<String, DistanceDuration> distanceDuration;
    private int votes;

    public Venue() {
        //default constructor
    }

    public Venue(float lat, float lng, String name, String address, String phoneNumber, String venueId) {
        this.venueCoordinates[0] = lat;
        this.venueCoordinates[1] = lng;
        this.venueName = name;
        this.venueAddress = address;
        this.venuePhoneNumber = phoneNumber;
        this.venueId = venueId;
        this.distanceDuration = new HashMap<>();
    }

    public void setVenueCoordinates(float lat, float lng) {
        this.venueCoordinates[0] = lat;
        this.venueCoordinates[1] = lng;
    }

    public float[] getVenueCoordinates() {
        return venueCoordinates;
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

    public void initUserDistanceDuration() {
        if(this.distanceDuration == null) {
            this.distanceDuration = new HashMap<>();
        }
    }

    public void storeUserDistanceDurationToVenue(String uid, DistanceDuration distanceDuration) {
        this.distanceDuration.put(uid, distanceDuration);
    }

    public Pair<String, String> getUserDistanceDurationToVenue(String uid, TravelMethod method) {
        return this.distanceDuration.get(uid).getDistanceDuration(method);
    }

    public void setVotes(int vote) {
        this.votes = vote;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((venueId == null) ? 0 : venueId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null || !(obj instanceof Venue))
            return false;

        Venue other = (Venue) obj;

        return this.getVenueId().equals(other.getVenueId());
    }
}
