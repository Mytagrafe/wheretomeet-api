package com.wheretomeet.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;

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
    HashSet<Home> homes;

    public User() {
        //default constructor
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.homes = new HashSet<>();
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.homes = new HashSet<>();
    }

    public HashSet<Home> getHomes() {
        return homes;
    }

    public void addHome(Home home) {
        if(this.homes == null) {
            homes = new HashSet<>();
        }
        homes.add(home);
    }

    public void removeHome(Home home) {
        homes.remove(home);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
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