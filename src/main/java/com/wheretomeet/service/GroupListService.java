package com.wheretomeet.service;

import java.util.NoSuchElementException;

import com.wheretomeet.entity.GroupsList;
import com.wheretomeet.model.LiteGroup;
import com.wheretomeet.repository.GroupsListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class GroupListService {
    @Autowired
    GroupsListRepository groupsListRepo;

    public GroupsList findUserById(String id) {
        return groupsListRepo.findById(id).orElse(null);
    }

    @Transactional
    public void save(GroupsList groupsList) {
        groupsListRepo.save(groupsList);
    }

    @Transactional
    public void delete(String userId) {
        groupsListRepo.deleteById(userId);
    }

    public void addGroupToUserGroupList(String userId, LiteGroup liteGroup) {
        GroupsList list = findUserById(userId);
        boolean added = list.addGroup(liteGroup);
        if(!added) {
            throw new IllegalArgumentException("group already exists in list");
        }
        save(list);
    }

    public void removeGroupToUserGroupList(String userId, LiteGroup liteGroup) {
        GroupsList list = findUserById(userId);
        boolean removed = list.removeGroup(liteGroup);
        if(!removed) {
            throw new NoSuchElementException("group not found in groupslist");
        }
        save(list);
    }
}
