package com.wheretomeet.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.wheretomeet.model.Group;
import com.wheretomeet.model.User;
import com.wheretomeet.repository.GroupRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.hamcrest.Matchers;
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

    @Test
    void testGetOneGroup() throws Exception { 
        User user1 = new User("Ayy", "123");
        User user2 = new User("Bee", "123");
        User user3 = new User("Cee", "123");

        Group group = new Group("g1", "123", user1, user2, user3);
        group.setGroupId("000000001");

        Mockito.when(groupRepo.findById("000000001")).thenReturn(Optional.of(group));
        
        mvc.perform(get("/group/id/{id}", "000000001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.groupName", Matchers.is("g1")))
            .andExpect(jsonPath("$.groupId", Matchers.is("000000001")));
    }

    @Test
    void testCreateGroup() throws Exception {
        User user1 = new User("Ayy", "123");
        User user2 = new User("Bee", "123");
        User user3 = new User("Cee", "123");

        Group group = new Group("g1", "123", user1, user2, user3);
        group.setGroupId("000000001");

        ObjectMapper mapper = new ObjectMapper();
        String jsonGroup = mapper.writeValueAsString(group);
        Mockito.when(groupRepo.save(group)).thenReturn(group);

        mvc.perform(post("/groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGroup)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.is("Group created")));
    }

    @Test
    void testDeleteGroup() throws Exception {
        User user1 = new User("Ayy", "123");
        User user2 = new User("Bee", "123");
        User user3 = new User("Cee", "123");

        Group group = new Group("g1", "123", user1, user2, user3);
        group.setGroupId("000000001");
        group.setGroupOwner(user1);

        Gson gson = new Gson();
        String jsonGroup = gson.toJson(group);
        Mockito.doNothing().when(groupRepo).deleteById("000000001");

        mvc.perform(delete("/group/id/{id}", "000000001")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGroup)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.is("Group deleted")));
    }
}
