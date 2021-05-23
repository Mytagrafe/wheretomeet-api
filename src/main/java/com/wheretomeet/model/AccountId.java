package com.wheretomeet.model;

import java.io.Serializable;

public class AccountId implements Serializable {
    private String username;
    private String userId;
    
    public AccountId() {
        //default constructor
    }

    public AccountId(String username, String id) {
        this.username = username;
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;

        AccountId other = (AccountId)o;

        if(other.username.equals(this.username) && other.userId.equals(this.userId)) {
            return true;
        }

        return false;
    }

}
