package com.wheretomeet.controller;

import java.util.Optional;

import com.wheretomeet.model.FriendsList;
import com.wheretomeet.model.User;
import com.wheretomeet.repository.FriendsListRepository;
import com.wheretomeet.repository.UserRepository;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FriendsListController.class)
public class FriendsListControllerTests {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    FriendsListRepository friendsRepo;

    @MockBean
    UserRepository userRepo;

    @Test
    void testGetUsersFriends() throws Exception { 
        FriendsList fl = new FriendsList("Ayy#1234");
        fl.addFriend(new User("Bee", "1234"));
        fl.addFriend(new User("Cee", "1234"));

        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(fl));

        mvc.perform(get("/friends/{id}", "Ayy#1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("friendsListOwner", Matchers.is("Ayy#1234")))
        .andExpect(jsonPath("friends", Matchers.hasSize(2)));
    }

    @Test
    void testAddFriend() throws Exception {
        User bee = new User("Bee", "1234");
        bee.setUserId("Bee#1234");
        FriendsList fl = new FriendsList("Ayy#1234");

        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(fl));
        Mockito.when(userRepo.findById("Bee#1234")).thenReturn(Optional.of(bee));

        mvc.perform(put("/friends/{userId}/add/{friendId}", "Ayy#1234", "Bee#1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("friendsListOwner", Matchers.is("Ayy#1234")))
        .andExpect(jsonPath("friends", Matchers.hasSize(1)));
    }

    @Test
    void testRemoveFriend() throws Exception {
        User bee = new User("Bee", "1234");
        bee.setUserId("Bee#1234");
        FriendsList fl = new FriendsList("Ayy#1234");
        fl.addFriend(bee);

        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(fl));
        Mockito.when(userRepo.findById("Bee#1234")).thenReturn(Optional.of(bee));

        mvc.perform(put("/friends/{userId}/remove/{friendId}", "Ayy#1234", "Bee#1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("friendsListOwner", Matchers.is("Ayy#1234")))
        .andExpect(jsonPath("friends", Matchers.hasSize(0)));
    }

    @Test
    void testDeleteFriendsList() throws Exception{
        FriendsList fl = new FriendsList("Ayy#1234");

        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(fl));

        mvc.perform(delete("/friends/destroy/{id}", "Ayy#1234"))
        .andExpect(status().isOk());
    }

}
