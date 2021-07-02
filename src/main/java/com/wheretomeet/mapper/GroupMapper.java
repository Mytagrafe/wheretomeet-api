package com.wheretomeet.mapper;

import com.wheretomeet.entity.Group;
import com.wheretomeet.model.LiteGroup;

public class GroupMapper {
    public LiteGroup toLiteGroup(Group g) {

        if(g == null) {
            return null;
        }

        LiteGroup liteGroup = new LiteGroup();
        liteGroup.setGroupName(g.getGroupName());
        liteGroup.setGroupId(g.getGroupId());

        return liteGroup;
    }
}
