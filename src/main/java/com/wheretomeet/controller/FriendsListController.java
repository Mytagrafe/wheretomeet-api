package com.wheretomeet.controller;

import com.wheretomeet.service.FriendsListService;

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
    FriendsListService friendsListService;

    @GetMapping("/friends/{userId}")
	public ResponseEntity<?> getUsersFriendsList(@PathVariable("userId") String userId) {
		return new ResponseEntity<>(friendsListService.getUsersFriendsList(userId), HttpStatus.OK);
    }
    
    @PutMapping("/friends/{userId}/add/{friendId}")
    public ResponseEntity<?> addFriendToList(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId) {
        return new ResponseEntity<>(friendsListService.addFriendToUserFriendList(userId, friendId), HttpStatus.OK);
    }

    @PutMapping("/friends/{userId}/remove/{friendId}")
    public ResponseEntity<?> removeFriendFromList(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId) {
        return new ResponseEntity<>(friendsListService.removeFriendFromUserFriendList(userId, friendId), HttpStatus.OK);
    }
}
