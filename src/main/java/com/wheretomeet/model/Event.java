package com.wheretomeet.model;
import java.io.Serializable;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.javatuples.Pair;

public class Event implements Serializable {
    final static Logger log = LoggerFactory.getLogger(Event.class);
    private String groupName;
    private long startTime;
    private long endTime;
    private String eventLocation;

    public Event() {
        //default constructor
    }

    public Event(String groupName, long startTime, long endTime, String eventLocation) {
        this.groupName = groupName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventLocation = eventLocation;
    }

    public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

    public String getEventLocation() {
        return eventLocation;
    }																													

    public void setGroupName(String groupName) {																								
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
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
        if(obj == null || !(obj instanceof Venue)) {
            return false;
        }
        Event other = (Event) obj;
        return (this.getGroupName().equals(other.getGroupName()) && this.getStartTime() == (other.getStartTime()));
    }
}
