package com.wheretomeet.model;

import java.io.Serializable;
import java.util.HashSet;

import com.wheretomeet.entity.User;
import com.wheretomeet.mapper.UserMapper;

public class LiteGroup implements Serializable {
    private String groupId;
    private String groupName;
    private HashSet<LiteUser> members;
    private LiteUser groupOwner;

    public LiteGroup() {
        //default constructor
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LiteUser getGroupOwner() {
        return groupOwner;
    }
    
    public void setGroupOwner(LiteUser groupOwner) {
        this.groupOwner = groupOwner;
    }
    
    public HashSet<LiteUser> getMembers() {
        return members;
    }

    public void setMembers(HashSet<LiteUser> members) {
        this.members = members;
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
        if(obj == null || !(obj instanceof LiteGroup)) {
            return false;
        }
        
        LiteGroup other = (LiteGroup)obj;

        return this.getGroupId().equals(other.getGroupId());
    }


}
