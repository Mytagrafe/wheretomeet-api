package com.wheretomeet.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.LoggerFactory;

import com.wheretomeet.service.GroupListService;

import org.slf4j.Logger;

@RestController
public class GroupsListController {

    final static Logger log = LoggerFactory.getLogger(GroupsListController.class);

    @Autowired
    private GroupListService groupListService;

    @GetMapping("/groupsList/{uid}")
    public ResponseEntity<?> getUsersGroupList(@PathVariable("uid") String userId) {
        return new ResponseEntity<>(groupListService.getUsersGroupsList(userId), HttpStatus.OK);
    }
    
    @PutMapping("/groupsList/{uid}/add/{gid}")
    public ResponseEntity<?> addGroupToGroupList(@PathVariable("uid") String userId, @PathVariable("gid") String groupId) {
        return new ResponseEntity<>(groupListService.addGroupToUserGroupList(userId, groupId), HttpStatus.OK);
    }

    @PutMapping("/groupsList/{uid}/remove/{gid}")
    public ResponseEntity<?> removeGroupFromGroupList(@PathVariable("uid") String userId, @PathVariable("gid") String groupId) {

        return new ResponseEntity<>(groupListService.removeGroupFromUserGroupList(userId, groupId), HttpStatus.OK);
    }
}
