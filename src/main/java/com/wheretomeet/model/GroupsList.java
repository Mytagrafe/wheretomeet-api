package com.wheretomeet.model;

import java.io.Serializable;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name =  "groups_list")
public class GroupsList implements Serializable {
    @Id
    private String userId;
    private HashSet<Group> groups;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public GroupsList() {
        // default constructor
    }

    public GroupsList(String userId) {
        this.userId = userId;
        this.groups = new HashSet<>();
    }

    public String getGroupsListOwner() {
        return userId;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public HashSet<Group> getGroups() {
        return groups;
    }
}
