package com.wheretomeet.controller;

import java.util.Optional;

import com.wheretomeet.model.FriendsList;
import com.wheretomeet.model.User;
import com.wheretomeet.repository.FriendsListRepository;
import com.wheretomeet.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
public class FriendsListController {

    final static Logger log = LoggerFactory.getLogger(FriendsListController.class);

    @Autowired
    FriendsListRepository friendsRepo;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/friends/{userId}")
	public ResponseEntity<?> getUsersFriendsList(@PathVariable("userId") String userId) {
		Optional<FriendsList> friendsList = friendsRepo.findById(userId);
		if(friendsList.isPresent()) {
			return ResponseEntity.ok().body(friendsList.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/friends/{userId}/add/{friendId}")
    public ResponseEntity<?> addFriendToList(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId) {
        FriendsList friendsList = friendsRepo.findById(userId).orElse(null);
        if(friendsList != null) {
            User friend = userRepo.findById(friendId).orElse(null);
            if(friend != null) {
                boolean added = friendsList.addFriend(friend);
                if(!added) {
                    return new ResponseEntity<>("user does not exist", HttpStatus.NOT_FOUND);
                }
                friendsRepo.save(friendsList);
                return new ResponseEntity<>(friendsList, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/friends/{userId}/remove/{friendId}")
    public ResponseEntity<?> removeFriendFromList(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId) {
        FriendsList friendsList = friendsRepo.findById(userId).orElse(null);

        if(friendsList != null) {
            User friend = userRepo.findById(friendId).orElse(null);
            if(friend != null) {
                try {
                    boolean removed = friendsList.removeFriend(friend);
                    if(!removed) {
                        return new ResponseEntity<>("user does not exist", HttpStatus.NOT_FOUND);
                    }
                }
                catch(NullPointerException e) {
                    log.info(e.getMessage());
                }
                friendsRepo.save(friendsList);
                return new ResponseEntity<>(friendsList, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
