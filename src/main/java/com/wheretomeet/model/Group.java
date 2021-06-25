package com.wheretomeet.model;

import java.util.HashSet;
import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "groups_table")
public class Group implements Serializable {

    @GeneratedValue(generator = "groupId-generator")
    @GenericGenerator(name = "groupId-generator", strategy = "com.wheretomeet.util.WhereToMeetIdGenerator")
    private @Id String groupId; 
    private String groupName;
    private String groupPassword;
    @JoinColumn(name = "group_owner", referencedColumnName = "userId")
    private @OneToOne User groupOwner;
    private HashSet<User> groupMembers;
    private HashMap<String, Venue> venues;
    private ArrayList<Timeframe> freeTime;

    public Group() {
        //default constructor
    }

    public Group(String groupName, String password, User...users) {
        this.groupName = groupName;
        this.groupPassword = password;
        if(users != null){
            this.groupMembers = new HashSet<User>();
            for (User user : users) {
                groupMembers.add(user);
            }
        }
        this.venues = new HashMap<String, Venue>();
        this.freeTime = new ArrayList<Timeframe>();
    }

    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPassword() {
        return groupPassword;
    }

    public void setGroupPassword(String password) {
        this.groupPassword = password;
    }

    public User getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(User groupOwner) {
        this.groupOwner = groupOwner;
    }

    public HashSet<User> getGroupMembers() {
        return groupMembers;
    }

    public boolean addGroupMember(User user){
        if(groupMembers == null) {
            groupMembers = new HashSet<>();
        }
        if(groupMembers.contains(user)) {
            return true;
        }
        return groupMembers.add(user);
    }
    
    public boolean removeGroupMember(User user) {
        if(groupMembers == null) {
            throw new NullPointerException("Group's members list is null");
        }
        return groupMembers.remove(user);
    }

    public String getGroupId(){
        return groupId;
    }

    public void setGroupId(String id) {
        this.groupId = id;
    }

    public HashMap<String, Venue> getGroupVenues(){
        return venues;
    }

    public void initGroupVenues() {
        if(this.venues == null) {
            this.venues = new HashMap<>();
        }
    }

    public void addGroupVenue(String venueId, Venue venue) {
        this.venues.put(venueId, venue);
    }

    public void removeGroupVenue(String venueId) {
        this.venues.remove(venueId);
    }

    public void addTimeframe(Timeframe timeframe) {
        if(this.freeTime == null)
            this.freeTime = new ArrayList<Timeframe>();
        freeTime.add(timeframe);
    }

    public ArrayList<Timeframe> getTimeframes() {
        return this.freeTime;
    }

    public void removeTimeframe(Timeframe timeframe) {
        if(this.freeTime != null)
            freeTime.remove(timeframe);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Group)) {
            return false;
        }
        
        Group other = (Group)obj;

        return this.getGroupId().equals(other.getGroupId());
    }
}