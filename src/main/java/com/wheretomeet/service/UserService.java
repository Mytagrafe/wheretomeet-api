package com.wheretomeet.service;

import com.wheretomeet.entity.User;
import com.wheretomeet.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public User findUserById(String id) {
        return userRepo.findById(id).orElse(null);
    }

    @Transactional
    public void save(User userEntity) {
        userRepo.save(userEntity);
    }

    // General user management stuff
    public void createNewUser() {

    }

    public void getUser() {
        // should only return username, id, and email
    }

    public void deleteUser() {

    }

    // User Groups -- Are these needed....
    public void getUserGroups() {

    }

    public void addGroupToUser() {

    }

    public void removeGroupFromUser() {

    }

    // User Homes:
    public void getUserHomes() {

    }

    public void addUserHome() {

    }

    public void removeUserHome() {

    }

    // User Events:
    public void getUserEvents() {

    }

    public void addEventToUser() {
    
    }

    public void removeEventFromUser() {

    }
    
}
