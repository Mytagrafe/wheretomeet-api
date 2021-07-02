package com.wheretomeet.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.wheretomeet.entity.Group;
import com.wheretomeet.entity.GroupsList;
import com.wheretomeet.model.LiteGroup;
import com.wheretomeet.repository.GroupsListRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class GroupsListServiceTests {
    
    @InjectMocks
    GroupListService groupsListService;

    @Mock
    GroupsListRepository groupsListRepo;

    @Mock
    GroupService groupService;

    private GroupsList list = new GroupsList("Ayy#1234");
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
    public void testFindGroupsListById() {
        Mockito.when(groupsListRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        HashSet<LiteGroup> gl = groupsListService.getUsersGroupsList("Ayy#1234");
        assertEquals(0, gl.size());
        assertNotNull(gl);
    }

    @Test
    public void testAddGroupToUserGroupList() {
        Group group = new Group();
        group.setGroupId("123456789");

        Mockito.when(groupsListRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        Mockito.when(groupService.findGroupById("123456789")).thenReturn(group);

        HashSet<LiteGroup> groups = groupsListService.addGroupToUserGroupList("Ayy#1234", "123456789");
        assertEquals(1, groups.size());
    }
    
    @Test
    public void testAddNullGroupToGroupsList() {
        Mockito.when(groupsListRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        assertThrows(IllegalArgumentException.class, () -> {
            groupsListService.addGroupToUserGroupList("Ayy#1234", "987654321");
        });
    }


    @Test
    public void testRemoveGroupFromUserGroupList() {
        Group group = new Group();
        group.setGroupId("123456789");

        LiteGroup liteGroup = new LiteGroup();
        liteGroup.setGroupId(group.getGroupId());
        liteGroup.setGroupName(group.getGroupName());

        list.addGroup(liteGroup);

        Mockito.when(groupsListRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        Mockito.when(groupService.findGroupById("123456789")).thenReturn(group);

        HashSet<LiteGroup> groups = groupsListService.removeGroupFromUserGroupList("Ayy#1234", "123456789");
        assertEquals(0, groups.size());
    }

    @Test
    public void testRemoveNullGroupToGroupsList() {
        Mockito.when(groupsListRepo.findById("Ayy#1234")).thenReturn(Optional.of(list));
        assertThrows(NoSuchElementException.class, () -> {
            groupsListService.removeGroupFromUserGroupList("Ayy#1234", "987654321");
        });
    }

}
