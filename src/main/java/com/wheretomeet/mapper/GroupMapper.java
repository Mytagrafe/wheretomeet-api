package com.wheretomeet.mapper;

import com.wheretomeet.entity.Group;
import com.wheretomeet.model.LiteGroup;

public class GroupMapper {

    private UserMapper userMapper = new UserMapper();

    public LiteGroup toLiteGroup(Group g) {

        if(g == null) {
            return null;
        }

        LiteGroup liteGroup = new LiteGroup();
        liteGroup.setGroupName(g.getGroupName());
        liteGroup.setGroupId(g.getGroupId());
        liteGroup.setGroupOwner(userMapper.toLiteUser(g.getGroupOwner()));
        liteGroup.setMembers(g.getGroupMembers());

        return liteGroup;
    }
}
