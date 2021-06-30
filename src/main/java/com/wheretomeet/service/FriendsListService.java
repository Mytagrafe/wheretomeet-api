package com.wheretomeet.service;

import java.util.NoSuchElementException;

import com.wheretomeet.entity.FriendsList;
import com.wheretomeet.model.LiteUser;
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

    @Transactional
    public void delete(String userId) {
        friendsRepo.deleteById(userId);
    }

    public void addUserToUserFriendList(String userId, LiteUser liteUser) {
        FriendsList list = findUserById(userId);
        boolean added = list.addFriend(liteUser);
        if(!added) {
            throw new IllegalArgumentException("friend already exists in list");
        }
        save(list);
    }

    public void removeUserToUserFriendList(String userId, LiteUser liteUser) {
        FriendsList list = findUserById(userId);
        boolean removed = list.removeFriend(liteUser);
        if(!removed) {
            throw new NoSuchElementException("user not found in friendslist");
        }
        save(list);
    }
}
