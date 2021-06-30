package com.wheretomeet.service;

import com.wheretomeet.entity.FriendsList;
import com.wheretomeet.repository.FriendsListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class FriendsListService {
    @Autowired
    FriendsListRepository friendsRepo;

    public FriendsList findUserById(String id) {
        return friendsRepo.findById(id).orElse(null);
    }

    @Transactional
    public void save(FriendsList friendsList) {
        friendsRepo.save(friendsList);
    }

}
