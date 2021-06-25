package com.wheretomeet.model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Timeframe implements Serializable {
    final static Logger log = LoggerFactory.getLogger(Timeframe.class);
    private @Id String userId;
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
