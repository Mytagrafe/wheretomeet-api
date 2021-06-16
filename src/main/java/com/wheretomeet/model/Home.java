package com.wheretomeet.model;

import java.io.Serializable;

import com.wheretomeet.model.DistanceDuration.TravelMethod;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.javatuples.Pair;

public class Home implements Serializable{
    final static Logger log = LoggerFactory.getLogger(Home.class);

    private float[] homeCoordinates = new float[2];
    private String homeName;
    private String homeAddress;

    public Home() {
        //default constructor
    }

    public Home(float lat, float lng, String name, String address) {
        this.homeCoordinates[0] = lat;
        this.homeCoordinates[1] = lng;
        this.homeName = name;
        this.homeAddress = address;
    }

    public void setHomeCoordinates(float lat, float lng) {
        this.homeCoordinates[0] = lat;
        this.homeCoordinates[1] = lng;
    }

    public float[] getHomeCoordinates() {
        return homeCoordinates;
    }

    public void setHomeName(String name) {
        this.homeName = name;
    }

    public String getHomeName() {
        return homeName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String address) {
        this.homeAddress = address;
    }

    @Override
    public boolean equals(Object obj){

        if(obj == null || getClass() != obj.getClass())
            return false;

        Home other = (Home) obj;

        return this.homeAddress.equals( other.getHomeAddress() );
    }
}
