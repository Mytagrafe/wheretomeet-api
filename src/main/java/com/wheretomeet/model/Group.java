package com.wheretomeet.model;

import java.io.Serializable;
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
    private ArrayList<User> groupMembers;

    public Group() {
        //default constructor
    }

    public Group(String groupName, String password, User...users) {
        this.groupName = groupName;
        this.groupPassword = password;
        if(users != null){
            this.groupMembers = new ArrayList<User>();
            for (User user : users) {
                groupMembers.add(user);
            }
        }
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

    public ArrayList<User> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(ArrayList<User> members) {
        this.groupMembers = members;
    }

    public String getGroupId(){
        return groupId;
    }

    public void setGroupId(String id) {
        this.groupId = id;
    }
}