package com.wheretomeet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import com.wheretomeet.entity.Group;
import com.wheretomeet.entity.User;
import com.wheretomeet.mapper.GroupMapper;
import com.wheretomeet.mapper.UserMapper;
import com.wheretomeet.model.LiteGroup;
import com.wheretomeet.model.LiteUser;
import com.wheretomeet.model.Timeframe;
import com.wheretomeet.model.Venue;
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
    UserMapper userMapper;

    @Autowired
    GroupMapper groupMapper;

    // General group management stuff
    public void createGroup(String creatorId, String groupName, String password) {
        
        User owner = userService.findUserById(creatorId);

        if(owner == null) {
            throw new NullPointerException("Cannot find user with id:" + creatorId);
        }

        LiteUser liteUser = userMapper.toLiteUser(owner);

        Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupPassword(password);
        group.addGroupMember(liteUser);

        LiteGroup liteGroup = groupMapper.toLiteGroup(group);

        groupsListService.addGroupToUserGroupList(creatorId, liteGroup);
        groupRepo.save(group);
    }

    public void deleteGroup(String groupId) {
        groupRepo.deleteById(groupId);
    }

    public Group getGroupById(String groupId) {
        return groupRepo.findById(groupId).orElse(null);
    }


    // Group Membership
    public LiteUser addUserToGroup(String groupId, String newMemberId) {
        Group group = getGroupById(groupId);
        User newMember = userService.findUserById(newMemberId);
        
        if(newMember == null) {
            throw new NullPointerException("Cannot find user with id:" + newMemberId);
        }

        LiteUser liteUser = userMapper.toLiteUser(newMember);
        LiteGroup liteGroup = groupMapper.toLiteGroup(group);

        boolean added = group.addGroupMember(liteUser);
        if(!added) {
            throw new IllegalArgumentException("User is already a member of the group");
        }
        groupsListService.addGroupToUserGroupList(newMemberId, liteGroup);
        groupRepo.save(group);

        return liteUser;
    }   

    public void removeUserFromGroup(String groupId, String memberId) {
        Group group = getGroupById(groupId);
        User toRemove = userService.findUserById(memberId);

        if(toRemove == null) {
            throw new NullPointerException("Cannot find user with id:" + memberId);
        }

        LiteGroup liteGroup = groupMapper.toLiteGroup(group);
        LiteUser liteUser = userMapper.toLiteUser(toRemove);

        boolean removed = group.removeGroupMember(liteUser);

        if(!removed) {
            throw new NoSuchElementException("Cannot find user " + memberId + " to remove from group");
        }   

        groupsListService.removeGroupFromUserGroupList(memberId, liteGroup);
        groupRepo.save(group);
    }

    //Group Venues stuff:
    public HashMap<String, Venue> getGroupVenues(String groupId) {
        Group group = getGroupById(groupId);
        return group.getGroupVenues();
    }

    public HashMap<String, Venue> addVenueToGroup(String groupId, Venue venue) {
        Group group = getGroupById(groupId);

        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }

        group.addGroupVenue(venue.getVenueId(), venue);
        groupRepo.save(group);

        return group.getGroupVenues();
    }

    public HashMap<String, Venue> removeVenueFromGroup(String groupId, String venueId) {
        Group group = getGroupById(groupId);

        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }

        group.removeGroupVenue(venueId);
        groupRepo.save(group);

        return group.getGroupVenues();
    }

    // Group Timeframe stuff
    public ArrayList<Timeframe> getGroupTimeframes(String groupId) {
        Group group = getGroupById(groupId);

        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }
        
        return group.getTimeframes();
    }

    public ArrayList<Timeframe> addTimeframeToGroup(String groupId, Timeframe timeframe) {
        Group group = getGroupById(groupId);

        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }

        group.addTimeframe(timeframe);
        groupRepo.save(group);
        
        return group.getTimeframes();
    }

    public ArrayList<Timeframe> removeTimeframeToGroup(String groupId, Timeframe timeframe) {
        Group group = getGroupById(groupId);

        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }

        group.removeTimeframe(timeframe);
        groupRepo.save(group);

        return group.getTimeframes();
    }


}
