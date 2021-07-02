package com.wheretomeet.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.wheretomeet.entity.FriendsList;
import com.wheretomeet.entity.User;
import com.wheretomeet.model.LiteUser;
import com.wheretomeet.repository.FriendsListRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FriendsListServiceTests {

    @InjectMocks
    FriendsListService friendsListService;

    @Mock
    FriendsListRepository friendsRepo;

    @Mock
    UserService userService;

    private FriendsList list = new FriendsList("Ayy#1234");
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown()  throws Exception {
        closeable.close();
    }

    @Test
    public void testGetUsersFriends() throws Exception {
        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        HashSet<LiteUser> fl = friendsListService.getUsersFriendsList("Ayy#1234");

        assertNotNull(fl);
        assertEquals(0, fl.size());
    }

    @Test
    public void testGetUsersFriendsNull() throws Exception {
        assertThrows(NullPointerException.class, () -> {
            friendsListService.getUsersFriendsList("Bee#1234");
        });
    }

    @Test
    public void testAddFriend() throws Exception {
        User friend = new User();
        friend.setUserId("Bee#1234");
        friend.setUsername("Bee");

        LiteUser liteFriend = new LiteUser();
        liteFriend.setUserId(friend.getUserId());
        liteFriend.setUserId(friend.getUsername());
        
        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        Mockito.when(userService.findUserById("Bee#1234")).thenReturn(friend);

        HashSet<LiteUser> fl = friendsListService.addFriendToUserFriendList("Ayy#1234", "Bee#1234");
        assertEquals(1, fl.size());
    }
    
    @Test
    public void testAddNullFriend() {
        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        assertThrows(IllegalArgumentException.class, () -> {
            friendsListService.addFriendToUserFriendList("Ayy#1234","Bee#1234");
        });
    }

    @Test
    public void testRemoveFriend() throws Exception {
        User friend = new User();
        friend.setUserId("Bee#1234");
        friend.setUsername("Bee");

        LiteUser liteFriend = new LiteUser();
        liteFriend.setUserId(friend.getUserId());
        liteFriend.setUsername(friend.getUsername());

        list.addFriend(liteFriend);

        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        Mockito.when(userService.findUserById("Bee#1234")).thenReturn(friend);
    
        HashSet<LiteUser> fl = friendsListService.removeFriendFromUserFriendList("Ayy#1234", "Bee#1234");
        assertEquals(0, fl.size());
    }

    @Test
    public void testRemoveNullFriend() {
        Mockito.when(friendsRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        assertThrows(NoSuchElementException.class, () -> {
            friendsListService.removeFriendFromUserFriendList("Ayy#1234","Bee#1234");
        });
    }
}
