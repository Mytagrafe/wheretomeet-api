package com.wheretomeet.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@IdClass(AccountId.class)
@Table(name = "users_table")
public class User {  

    
    @GeneratedValue(generator = "userId-generator")
    @GenericGenerator(name = "userId-generator", strategy = "com.wheretomeet.util.WhereToMeetIdGenerator")
    @Column(name = "user_id")
    private @Id String userId;

    @Column(name = "username")
    private @Id String username;

    @Column(name = "password")
    private String password;
    
    public User() {
        //default constructor
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

} 