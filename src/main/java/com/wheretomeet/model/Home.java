package com.wheretomeet.model;

import java.io.Serializable;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Home implements Serializable {
    final static Logger log = LoggerFactory.getLogger(Home.class);

    private float[] homeCoordinates = new float[2];
    private String homeName;
    private String homeAddress;
    private String id;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Home other = (Home) obj;
        return this.homeAddress.equals( other.getHomeAddress() );
    }
}
