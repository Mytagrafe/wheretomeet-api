package com.wheretomeet.mapper;

import com.wheretomeet.entity.User;
import com.wheretomeet.model.LiteUser;

public class UserMapper {
    
    public LiteUser toLiteUser(User u) {
        LiteUser liteUser = new LiteUser();

        liteUser.setUsername(u.getUsername());
        liteUser.setUserId(u.getUserId());
        liteUser.setDisplayName(u.getDisplayName());
        liteUser.setAvatarUrl(u.getAvatarUrl());   

        return liteUser;
    }
}
