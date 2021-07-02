package com.wheretomeet.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wheretomeet.model.Event;
import com.wheretomeet.model.Home;

import java.util.HashSet;
import java.util.ArrayList;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users_table")
public class User implements Serializable { 

    @GeneratedValue(generator = "userId-generator")
    @GenericGenerator(name = "userId-generator", strategy = "com.wheretomeet.util.WhereToMeetIdGenerator")
    private @Id String userId;
    private String username;
    private String password;
    private String email;
    private String displayName;
    private String avatarUrl;
    private HashSet<Home> homes;
    private ArrayList<Event> events;

    public User() {
        //default constructor
        this.homes = new HashSet<>();
        this.events = new ArrayList<Event>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.homes = new HashSet<>();
        this.events = new ArrayList<Event>();
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.homes = new HashSet<>();
        this.events = new ArrayList<Event>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String url) {
        this.avatarUrl = url;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public boolean addEvent(Event event) {
        if(this.events == null)
            events = new ArrayList<>();
        return events.add(event);
    }

    public boolean removeEvent(Event event) {
        if(this.events == null) {
            return false;
        }
        return events.remove(event);
    }

    public HashSet<Home> getHomes() {
        return homes;
    }

    public boolean addHome(Home home) {
        if(this.homes == null) {
            homes = new HashSet<>();
        }
        if(homes.contains(home)) {
            return false;
        }
        return homes.add(home);
    }

    public boolean removeHome(Home home) {
        if(homes == null) {
            throw new NullPointerException("user's homes list is null");
        }
        return homes.remove(home);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        return this.getUserId().equals(other.getUserId());
    }
} 