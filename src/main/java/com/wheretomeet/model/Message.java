package com.wheretomeet.model;

import java.io.Serializable;

public class Message implements Serializable {

    private String message;
    private String userId;
    private long timestamp;

    public Message() {
        // default constructor
    }

    public Message(String message, String userId, long timestamp) {
        this.message = message;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return userId;
    }

    public void setSender(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
