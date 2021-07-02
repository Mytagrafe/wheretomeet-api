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
    private GroupRepository groupRepo;

    @Autowired 
    private UserService userService;

    @Autowired
    private GroupListService groupsListService;

    private UserMapper userMapper = new UserMapper();

    private GroupMapper groupMapper = new GroupMapper();

    protected Group findGroupById(String id) {
        return groupRepo.findById(id).orElse(null);
    }

    // General group management stuff
    public LiteGroup getGroupById(String groupId) {
        return groupMapper.toLiteGroup(groupRepo.findById(groupId).orElse(null));
    }

    public LiteGroup createGroup(String creatorId, String groupName, String password) {
        
        User owner = userService.findUserById(creatorId);

        if(owner == null) {
            throw new NullPointerException("Cannot find user with id:" + creatorId);
        }

        LiteUser liteUser = userMapper.toLiteUser(owner);

        Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupPassword(password);
        group.addGroupMember(liteUser);
        groupRepo.save(group);

        groupsListService.addGroupToUserGroupList(creatorId, group.getGroupId());

        return groupMapper.toLiteGroup(group);
    }

    public void deleteGroup(String groupId) {
        groupRepo.deleteById(groupId);
    }

    // Group Membership
    public LiteUser addUserToGroup(String groupId, String newMemberId) {
        Group group = findGroupById(groupId);
        User newMember = userService.findUserById(newMemberId);
        
        if(newMember == null) {
            throw new NullPointerException("Cannot find user with id:" + newMemberId);
        }

        LiteUser liteUser = userMapper.toLiteUser(newMember);

        boolean added = group.addGroupMember(liteUser);
        if(!added) {
            throw new IllegalArgumentException("User is already a member of the group");
        }

        groupRepo.save(group);
        groupsListService.addGroupToUserGroupList(newMemberId, group.getGroupId());

        return liteUser;
    }   

    public LiteUser removeUserFromGroup(String groupId, String memberId) {
        Group group = findGroupById(groupId);
        User toRemove = userService.findUserById(memberId);

        if(toRemove == null) {
            throw new NullPointerException("Cannot find user with id:" + memberId);
        }

        LiteUser liteUser = userMapper.toLiteUser(toRemove);

        boolean removed = group.removeGroupMember(liteUser);

        if(!removed) {
            throw new NoSuchElementException("Cannot find user " + memberId + " to remove from group");
        }   

        groupRepo.save(group);
        groupsListService.removeGroupFromUserGroupList(memberId, group.getGroupId());

        return liteUser;
    }

    //Group Venues stuff:
    public HashMap<String, Venue> getGroupVenues(String groupId) {
        Group group = findGroupById(groupId);
        return group.getGroupVenues();
    }

    public HashMap<String, Venue> addVenueToGroup(String groupId, Venue venue) {
        Group group = findGroupById(groupId);

        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }

        group.addGroupVenue(venue);
        groupRepo.save(group);

        return group.getGroupVenues();
    }

    public HashMap<String, Venue> removeVenueFromGroup(String groupId, String venueId) {
        Group group = findGroupById(groupId);
        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }

        group.removeGroupVenue(venueId);
        groupRepo.save(group);

        return group.getGroupVenues();
    }

    // Group Timeframe stuff
    public ArrayList<Timeframe> getGroupTimeframes(String groupId) {
        Group group = findGroupById(groupId);

        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }
        
        return group.getTimeframes();
    }

    public ArrayList<Timeframe> addTimeframeToGroup(String groupId, Timeframe timeframe) {
        Group group = findGroupById(groupId);

        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }

        group.addTimeframe(timeframe);
        groupRepo.save(group);

        return group.getTimeframes();
    }

    public ArrayList<Timeframe> removeTimeframeFromGroup(String groupId, Timeframe timeframe) {
        Group group = findGroupById(groupId);

        if(group == null) {
            throw new NullPointerException("group with id " + groupId + " does not exist");
        }

        group.removeTimeframe(timeframe);
        groupRepo.save(group);

        return group.getTimeframes();
    }
}
