package com.wheretomeet.model;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groups_table")
public class Group {

    @Column(name = "group_id")
    private @Id @GeneratedValue long groupId; 
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "members")
    private ArrayList<User> groupMembers;

    public Group() {
        //default constructor
    }

    public Group(String groupName, User...users) {
        this.groupName = groupName;
        this.groupMembers = new ArrayList<User>();
        if(users != null){
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

    public ArrayList<User> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(ArrayList<User> members) {
        this.groupMembers = members;
    }

    public Long getGroupId(){
        return groupId;
    }

    public void setGroupId(Long id) {
        this.groupId = id;
    }

}