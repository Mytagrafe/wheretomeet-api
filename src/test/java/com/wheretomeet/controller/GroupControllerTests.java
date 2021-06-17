package com.wheretomeet.controller;

import java.util.Optional;

import com.wheretomeet.model.Group;
import com.wheretomeet.model.User;
import com.wheretomeet.repository.GroupRepository;
import com.google.gson.Gson;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
public class GroupControllerTests {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    GroupRepository groupRepo;

    private User user1;
    private User user2;
    private User user3;
    private Group group;

    @BeforeEach
    void initUsersAndGroups() {
        user1 = new User("Ayy", "123");
        user2 = new User("Bee", "123");
        user3 = new User("Cee", "123");

        user1.setUserId("Ayy#1234");
        user2.setUserId("Bee#1234");
        user3.setUserId("Cee#1234");

        group = new Group("g1", "123", user1, user2, user3);
        group.setGroupOwner(user1);
        group.setGroupId("000000001");
    }

    @Test
    void testGetOneGroup() throws Exception { 
        Mockito.when(groupRepo.findById("000000001")).thenReturn(Optional.of(group));
        
        mvc.perform(get("/group/id/{id}", "000000001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.groupName", Matchers.is("g1")))
            .andExpect(jsonPath("$.groupId", Matchers.is("000000001")));
    }

    @Test
    void testCreateGroup() throws Exception {
        Gson gson = new Gson();
        String jsonGroup = gson.toJson(group);

        Mockito.when(groupRepo.save(group)).thenReturn(group);

        mvc.perform(post("/groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGroup)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testDeleteGroup() throws Exception {
        Gson gson = new Gson();
        String jsonGroup = gson.toJson(group);
        Mockito.doNothing().when(groupRepo).deleteById("000000001");

        mvc.perform(delete("/group/id/{id}", "000000001")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGroup)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
