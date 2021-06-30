package com.wheretomeet.service;

import com.wheretomeet.entity.GroupsList;
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
}
