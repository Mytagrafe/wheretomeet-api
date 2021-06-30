package com.wheretomeet.model;

public class LiteUser {
    String username;
    String userId;
    
    public LiteUser() {
        //defauly constructor
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof LiteUser)) {
            return false;
        }
        LiteUser other = (LiteUser) obj;
        return this.getUserId().equals(other.getUserId());
    }
}
