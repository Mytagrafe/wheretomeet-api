package com.wheretomeet.service;

import java.util.HashSet;
import java.util.NoSuchElementException;

import com.wheretomeet.entity.FriendsList;
import com.wheretomeet.entity.User;
import com.wheretomeet.mapper.UserMapper;
import com.wheretomeet.model.LiteUser;
import com.wheretomeet.repository.FriendsListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendsListService {
    @Autowired
    private FriendsListRepository friendsRepo;

    @Autowired
    private UserService userService;

    private UserMapper userMapper = new UserMapper();

    protected FriendsList findFriendsListById(String userId) {
        FriendsList friendsList = friendsRepo.findById(userId).orElse(null);
        if(friendsList == null) {
            throw new NullPointerException("cannot find find " + userId + "'s friendslist");
        }
        return friendsList;
    }

    @Transactional
    public void save(FriendsList friendsList) {
        friendsRepo.save(friendsList);
    }

    @Transactional
    public void delete(String userId) {
        friendsRepo.deleteById(userId);
    }

    public HashSet<LiteUser> getUsersFriendsList(String userId) {
        FriendsList list = findFriendsListById(userId);
        if(list == null) {
            throw new NullPointerException("user " + userId + "'s friendslist does not exist");
        }
        return list.getFriends();
    }

    public HashSet<LiteUser> addFriendToUserFriendList(String userId, String friendId) {
        FriendsList list = findFriendsListById(userId);
        User friend = userService.findUserById(friendId);

        boolean added = list.addFriend(userMapper.toLiteUser(friend));

        if(!added) {
            throw new IllegalArgumentException("friend already exists in list");
        }

        save(list);
        return list.getFriends();
    }

    public HashSet<LiteUser> removeFriendFromUserFriendList(String userId, String friendId) {
        FriendsList list = findFriendsListById(userId);
        User friend = userService.findUserById(friendId);

        boolean removed = list.removeFriend(userMapper.toLiteUser(friend));

        if(!removed) {
            throw new NoSuchElementException("user not found in friendslist");
        }

        save(list);
        return list.getFriends();
    }
}
