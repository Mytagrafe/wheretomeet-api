package com.wheretomeet.controller;

import java.util.Optional;

import com.wheretomeet.entity.FriendsList;
import com.wheretomeet.entity.User;
import com.wheretomeet.model.LiteUser;
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
        LiteUser bee = new LiteUser();
        LiteUser cee = new LiteUser();
        
        bee.setUserId("Bee#1234");
        cee.setUserId("Cee#1234");

        fl.addFriend(bee);
        fl.addFriend(cee);

        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(fl));

        mvc.perform(get("/friends/{id}", "Ayy#1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("friendsListOwner", Matchers.is("Ayy#1234")))
        .andExpect(jsonPath("friends", Matchers.hasSize(2)));
    }

    @Test
    void testAddFriend() throws Exception {
        FriendsList fl = new FriendsList("Ayy#1234");

        User bee = new User("Bee", "1234");
        bee.setUserId("Bee#1234");

        User cee = new User("Cee", "1234");
        cee.setUserId("Cee#1234");

        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(fl));
        Mockito.when(userRepo.findById("Bee#1234")).thenReturn(Optional.of(bee));
        Mockito.when(userRepo.findById("Cee#1234")).thenReturn(Optional.of(cee));

        mvc.perform(put("/friends/{userId}/add/{friendId}", "Ayy#1234", "Bee#1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("friendsListOwner", Matchers.is("Ayy#1234")))
        .andExpect(jsonPath("friends", Matchers.hasSize(1)));

        mvc.perform(put("/friends/{userId}/add/{friendId}", "Ayy#1234", "Cee#1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("friendsListOwner", Matchers.is("Ayy#1234")))
        .andExpect(jsonPath("friends", Matchers.hasSize(2)));

        mvc.perform(put("/friends/{userId}/add/{friendId}", "Ayy#1234", "Bee#1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("friendsListOwner", Matchers.is("Ayy#1234")))
        .andExpect(jsonPath("friends", Matchers.hasSize(2)));

    }

    @Test
    void testRemoveFriend() throws Exception {
        FriendsList fl = new FriendsList("Ayy#1234");

        User bee = new User("Bee", "1234");
        bee.setUserId("Bee#1234");

        LiteUser liteBee = new LiteUser();
        liteBee.setUserId(bee.getUserId());
        
        fl.addFriend(liteBee);

        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(fl));
        Mockito.when(userRepo.findById("Bee#1234")).thenReturn(Optional.of(bee));

        mvc.perform(put("/friends/{userId}/remove/{friendId}", "Ayy#1234", "Bee#1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("friendsListOwner", Matchers.is("Ayy#1234")))
        .andExpect(jsonPath("friends", Matchers.hasSize(0)));
    }
}
