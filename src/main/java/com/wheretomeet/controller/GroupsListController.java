package com.wheretomeet.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.LoggerFactory;

import com.wheretomeet.entity.Group;
import com.wheretomeet.entity.GroupsList;
import com.wheretomeet.mapper.GroupMapper;
import com.wheretomeet.repository.GroupRepository;
import com.wheretomeet.repository.GroupsListRepository;

import org.slf4j.Logger;

@RestController
public class GroupsListController {

    final static Logger log = LoggerFactory.getLogger(GroupsListController.class);

    @Autowired
    private GroupsListRepository groupsListRepo;
    
	@Autowired
	private GroupRepository groupRepo;

    @GetMapping("/groupsList/{uid}")
    public ResponseEntity<?> getUsersGroupList(@PathVariable("uid") String userId) {

        GroupsList groupList = groupsListRepo.findById(userId).orElse(null);
        if(groupList != null) {
            return new ResponseEntity<GroupsList>(groupList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/groupsList/{uid}/add/{gid}")
    public ResponseEntity<?> addGroupToGroupList(@PathVariable("uid") String userId, @PathVariable("gid") String groupId) {

        GroupsList groupsList = groupsListRepo.findById(userId).orElse(null);
        if(groupsList != null) {
            Group group = groupRepo.findById(groupId).orElse(null);
            if(group != null) {
                GroupMapper gMapper = new GroupMapper();
                groupsList.addGroup(gMapper.toLiteGroup(group));
                groupsListRepo.save(groupsList);
                return new ResponseEntity<>(groupsList, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/groupsList/{uid}/remove/{gid}")
    public ResponseEntity<?> removeGroupFromGroupList(@PathVariable("uid") String userId, @PathVariable("gid") String groupId) {

        GroupsList groupsList = groupsListRepo.findById(userId).orElse(null);
        if(groupsList != null) {
            Group group = groupRepo.findById(groupId).orElse(null);
            if(group != null) {
                GroupMapper gMapper = new GroupMapper();
                groupsList.removeGroup(gMapper.toLiteGroup(group));
                groupsListRepo.save(groupsList);
                return new ResponseEntity<>(groupsList, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
