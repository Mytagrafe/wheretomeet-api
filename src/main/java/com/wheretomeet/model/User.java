package com.wheretomeet.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @JsonBackReference
    private ArrayList<Group> groups;

    public User() {
        //default constructor
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.groups = new ArrayList<Group>();
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.groups = new ArrayList<Group>();
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

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public boolean equals(Object o) {
        if (!(o instanceof User)) {
          return false;
        }
        User other = (User) o;
        return userId.equals(other.userId);
    }
} 