package com.wheretomeet.model;

import java.io.Serializable;

public class LiteGroup implements Serializable {
    String groupId;
    String groupName;

    public LiteGroup() {
        //default constructor
    }

    public LiteGroup(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
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
