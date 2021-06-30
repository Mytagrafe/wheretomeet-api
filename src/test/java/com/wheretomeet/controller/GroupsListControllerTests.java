package com.wheretomeet.controller;

import java.util.Optional;

import com.wheretomeet.entity.Group;
import com.wheretomeet.entity.GroupsList;
import com.wheretomeet.model.LiteGroup;
import com.wheretomeet.repository.GroupRepository;
import com.wheretomeet.repository.GroupsListRepository;

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

@WebMvcTest(GroupsListController.class)
public class GroupsListControllerTests {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupsListRepository groupsListRepo;
    
	@MockBean
	private GroupRepository groupRepo;

    @Test
    void testGetUsersGroups() throws Exception { 

        GroupsList groupList = new GroupsList("Ayy#1234");

        Group group = new Group();
        group.setGroupId("123456789");

        LiteGroup liteGroup = new LiteGroup();
        liteGroup.setGroupId(group.getGroupId());

        groupList.addGroup(liteGroup);


        Mockito.when(groupsListRepo.findById("Ayy#1234")).thenReturn(Optional.of(groupList));
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        mvc.perform(get("/groupsList/{id}", "Ayy#1234"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("groups", Matchers.hasSize(1)));
    }

    @Test
    void testAddGroup() throws Exception {
        GroupsList groupList = new GroupsList("Ayy#1234");
        Group group = new Group();
        group.setGroupId("123456789");

        Mockito.when(groupsListRepo.findById("Ayy#1234")).thenReturn(Optional.of(groupList));
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        mvc.perform(put("/groupsList/{id}/add/{gid}", "Ayy#1234", "123456789"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("groups", Matchers.hasSize(1)));
    }

    @Test
    void testRemoveGroup() throws Exception {
        GroupsList groupList = new GroupsList("Ayy#1234");

        Group group = new Group();
        group.setGroupId("123456789");

        LiteGroup liteGroup = new LiteGroup();
        liteGroup.setGroupId(group.getGroupId());

        groupList.addGroup(liteGroup);

        Mockito.when(groupsListRepo.findById("Ayy#1234")).thenReturn(Optional.of(groupList));
        Mockito.when(groupRepo.findById("123456789")).thenReturn(Optional.of(group));

        mvc.perform(put("/groupsList/{id}/remove/{gid}", "Ayy#1234", "123456789"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("groups", Matchers.hasSize(0)));
    }
}
