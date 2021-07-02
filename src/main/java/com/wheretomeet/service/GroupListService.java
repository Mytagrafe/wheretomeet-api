package com.wheretomeet.service;

import java.util.HashSet;
import java.util.NoSuchElementException;

import com.wheretomeet.entity.Group;
import com.wheretomeet.entity.GroupsList;
import com.wheretomeet.mapper.GroupMapper;
import com.wheretomeet.model.LiteGroup;
import com.wheretomeet.repository.GroupsListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupListService {
    @Autowired
    private GroupsListRepository groupsListRepo;

    @Autowired
    private GroupService groupService;

    private GroupMapper groupMapper = new GroupMapper();

    public GroupsList findGroupListById(String id) {
        GroupsList groupsList = groupsListRepo.findById(id).orElse(null);
        if(groupsList == null) {
            throw new NullPointerException("cannot find find " + id + "'s groupslist");
        }
        return groupsList;
    }

    @Transactional
    public void save(GroupsList groupsList) {
        groupsListRepo.save(groupsList);
    }

    @Transactional
    public void delete(String userId) {
        groupsListRepo.deleteById(userId);
    }

    public HashSet<LiteGroup> getUsersGroupsList(String userId) {
        GroupsList list = findGroupListById(userId);
        if(list == null) {
            throw new NullPointerException("user " + userId + "'s friendslist does not exist");
        }
        return list.getGroups();
    }

    public HashSet<LiteGroup> addGroupToUserGroupList(String userId, String groupId) {
        GroupsList list = findGroupListById(userId);
        Group group = groupService.findGroupById(groupId);

        boolean added = list.addGroup(groupMapper.toLiteGroup(group));
        if(!added) {
            throw new IllegalArgumentException("group already exists in grouplist");
        }
        save(list);
        return list.getGroups();
    }

    public HashSet<LiteGroup> removeGroupFromUserGroupList(String userId, String groupId) {
        GroupsList list = findGroupListById(userId);
        Group group = groupService.findGroupById(groupId);

        boolean removed = list.removeGroup(groupMapper.toLiteGroup(group));
        if(!removed) {
            throw new NoSuchElementException("group not found in groupslist");
        }

        save(list);
        return list.getGroups();
    }
}
