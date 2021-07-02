package com.wheretomeet.entity;

import java.io.Serializable;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.wheretomeet.model.LiteGroup;


@Entity
@Table(name = "groups_list")
public class GroupsList implements Serializable {
    @Id
    private String userId;
    private HashSet<LiteGroup> groups;
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

    public boolean addGroup(LiteGroup group) {
        if(groups == null) {
            groups = new HashSet<>();
        }
        return group == null ? false: groups.add(group);
    }

    public boolean removeGroup(LiteGroup group) {
        if(groups == null) {
            throw new NullPointerException("user's group list is null");
        }
        return groups.remove(group);
    }

    public HashSet<LiteGroup> getGroups() {
        return groups;
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
        if(obj == null || !(obj instanceof GroupsList)) {
            return false;
        }
        GroupsList other = (GroupsList) obj;
        return this.getGroupsListOwner().equals(other.getGroupsListOwner());
    }
}
