package com.wheretomeet.model;

public class LiteUser {
    private String userId;
    private String username;
    private String displayName;
    private String avatarUrl;
    
    public LiteUser() {
        //default constructor
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
