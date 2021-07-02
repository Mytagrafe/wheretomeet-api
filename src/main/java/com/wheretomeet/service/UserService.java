package com.wheretomeet.service;

import java.util.ArrayList;
import java.util.HashSet;

import com.wheretomeet.entity.User;
import com.wheretomeet.mapper.UserMapper;
import com.wheretomeet.model.Event;
import com.wheretomeet.model.Home;
import com.wheretomeet.model.LiteUser;
import com.wheretomeet.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    private  UserMapper userMapper = new UserMapper();

    protected User findUserById(String id) {
        return userRepo.findById(id).orElse(null);
    }

    @Transactional
    public void save(User userEntity) {
        userRepo.save(userEntity);
    }

    @Transactional
    public void delete(String userId) {
        userRepo.deleteById(userId);
    }

    // General user management stuff
    public LiteUser createNewUser() {
        return null;
    }

    public LiteUser getUser() {
        // should only return username, id, and email
        return null;
    }

    public LiteUser deleteUser() {
        return null;
    }

    // User Homes:
    public HashSet<Home> getUserHomes() {
        return null;
    }

    public HashSet<Home> addUserHome() {
        return null;
    }

    public HashSet<Home> removeUserHome() {
        return null;
    }

    // User Events:
    public ArrayList<Event> getUserEvents() {
        return null;
    }

    public ArrayList<Event> addEventToUser() {
        return null; 
    }

    public ArrayList<Event> removeEventFromUser() {
        return null;
    }
    
}
