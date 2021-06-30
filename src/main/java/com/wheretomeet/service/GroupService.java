package com.wheretomeet.service;

import com.wheretomeet.entity.Group;
import com.wheretomeet.entity.User;
import com.wheretomeet.mapper.GroupMapper;
import com.wheretomeet.repository.GroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepo;

    @Autowired 
    UserService userService;

    @Autowired
    GroupListService groupsListService;

    @Autowired
    GroupMapper groupMapper;

    // General group management stuff
    public void createGroup(String creatorId, String groupName, String password) {
        
        User owner = userService.findUserById(creatorId);

        // Need to figure out error handling if owner == null
        Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupPassword(password);
        group.addGroupMember(owner);

        /**
         * 1. Map groupObject to LiteGroup
         * 2. Call user GroupList service to add the LiteGroup to User's GroupList
         */
        groupRepo.save(group);
    }

    public void deleteGroup() {

    }

    public void getGroup() {

    }

    public void addUserToGroup(String groupId, String newMemberId) {
        Group group = groupRepo.findById(groupId).orElse(null);
        User user = userService.findUserById(newMemberId);
        // Need to figure out error handling if group == null

        // Convert user to LiteUser then add to group:
        group.addGroupMember(user);
        groupRepo.save(group);
    }   

    public void removeUserFromGroup(String groupId, String newMemberId) {
        Group group = groupRepo.findById(groupId).orElse(null);
        User user = userService.findUserById(newMemberId);
        // Need to figure out error handling if group == null

        // Convert user to LiteUser then remove to group:
        group.removeGroupMember(user);
        groupRepo.save(group);
    }

    //Group Venues stuff:
    public void getGroupVenues() {

    }

    public void addVenueToGroup() {

    }

    public void removeVenueFromGroup() {

    }

    // Group Timeframe stuff
    public void getGroupTimeframes() {

    }

    public void addTimeframeToGroup() {

    }

    public void removeTimeframeToGroup() {

    }


}
