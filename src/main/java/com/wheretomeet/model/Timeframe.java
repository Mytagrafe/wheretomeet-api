package com.wheretomeet.model;
import java.io.Serializable;

public class Timeframe implements Serializable {
    private String userId;
    private long startTime;
    private long endTime;

    public Timeframe() {
        //default constructor
    }

    public Timeframe(String userId, long startTime, long endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.userId = userId;
    }

    public void setUserId(String userId) {
		this.userId = userId;
	}

    public String getUserId() {
        return userId;
    }																													

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Timeframe)) {
            return false;
        }
        Timeframe other = (Timeframe) obj;
        return (this.getUserId().equals(other.getUserId()) && this.getStartTime() == (other.getStartTime()));
    }
}
