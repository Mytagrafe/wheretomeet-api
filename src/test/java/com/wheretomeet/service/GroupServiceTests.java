package com.wheretomeet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.wheretomeet.entity.Group;
import com.wheretomeet.entity.User;
import com.wheretomeet.model.LiteGroup;
import com.wheretomeet.model.LiteUser;
import com.wheretomeet.model.Timeframe;
import com.wheretomeet.model.Venue;
import com.wheretomeet.repository.GroupRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class GroupServiceTests {
    
    @InjectMocks
    GroupService groupService;

    @Mock
    GroupRepository groupRepo;

    @Mock
    GroupListService groupListService;

    @Mock
    UserService userService;

    private AutoCloseable closeable;
    private Group group;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        group = new Group();
        group.setGroupId("123456789");
    }

    @AfterEach
    public void tearDown()  throws Exception {
        closeable.close();
    }

    @Test
    public void testGetGroupById() {
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        LiteGroup foundGroup = groupService.getGroupById("123456789");

        assertNotNull(foundGroup);
        assertEquals("123456789", group.getGroupId());
    }

    @Test
    public void testCreateGroup() {
        User owner = new User();
        owner.setUserId("Ayy#1234");
        Mockito.when(userService.findUserById("Ayy#1234")).thenReturn(owner);

        LiteGroup newGroup = groupService.createGroup("Ayy#1234", "squadW", "1111");
        
        assertNotNull(newGroup);
        assertEquals("squadW", newGroup.getGroupName());
    }

    @Test
    public void testAddUserToGroup() {
        User newbie = new User();
        newbie.setUserId("Bee#1234");

        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));
        Mockito.when(userService.findUserById("Bee#1234")).thenReturn(newbie);

        LiteUser liteNewbie = groupService.addUserToGroup("123456789", "Bee#1234");

        assertNotNull(liteNewbie);
        assertEquals("Bee#1234", liteNewbie.getUserId());
        assertEquals(1, group.getGroupMembers().size());
    }

    @Test
    public void testRemoveUserFromGroup() {
        User newbie = new User();
        newbie.setUserId("Bee#1234");

        LiteUser liteNewbie = new LiteUser();
        liteNewbie.setUserId("Bee#1234");

        group.addGroupMember(liteNewbie);

        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));
        Mockito.when(userService.findUserById("Bee#1234")).thenReturn(newbie);

        LiteUser removedUser = groupService.removeUserFromGroup("123456789", "Bee#1234");

        assertNotNull(removedUser);
        assertEquals(liteNewbie, removedUser);
        assertEquals(0, group.getGroupMembers().size());
    }

    @Test
    public void testGetGroupVenues() {
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        HashMap<String, Venue> venues = groupService.getGroupVenues("123456789");

        assertNotNull(venues);
    }

    @Test
    public void testAddVenuesToGroup() {
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        Venue venue = new Venue();
        venue.setVenueId("12345");

        HashMap<String, Venue> venues = groupService.addVenueToGroup("123456789", venue);

        assertNotNull(venues);
        assertEquals(1, venues.size());
    }

    @Test
    public void testRemoveVenueFromGroup() {
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        Venue venue = new Venue();
        venue.setVenueId("12345");

        group.addGroupVenue(venue);

        HashMap<String, Venue> venues = groupService.removeVenueFromGroup("123456789", "12345");

        assertNotNull(venues);
        assertEquals(0, venues.size());
    }

    @Test
    public void testGetGroupTimeframes() {
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        ArrayList<Timeframe> times = groupService.getGroupTimeframes("123456789");

        assertNotNull(times);
    }

    @Test
    public void testAddTimeframeToGroup() {
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        Timeframe freetime = new Timeframe();
        freetime.setUserId("Ayy#1234");

        ArrayList<Timeframe> times = groupService.addTimeframeToGroup("123456789", freetime);

        assertNotNull(times);
        assertEquals(1, times.size());
        assertEquals("Ayy#1234", times.get(0).getUserId());
    }

    @Test
    public void testRemoveTimeframeFromGroup() {
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        Timeframe freetime = new Timeframe();
        freetime.setUserId("Ayy#1234");
        group.addTimeframe(freetime);

        ArrayList<Timeframe> times = groupService.removeTimeframeFromGroup("123456789", freetime);

        assertNotNull(times);
        assertEquals(0, times.size());
    }
}
