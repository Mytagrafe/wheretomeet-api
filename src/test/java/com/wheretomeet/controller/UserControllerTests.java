package com.wheretomeet.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.wheretomeet.model.AccountId;
import com.wheretomeet.model.User;
import com.wheretomeet.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;  

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

@WebMvcTest(UserController.class)
public class UserControllerTests {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    UserRepository userRepo;

    @Test
    void testGetAllUsers() throws Exception { 
        User user1 = new User("Ayy", "123");
        User user2 = new User("Bee", "123");
        User user3 = new User("Cee", "123");
        List<User> users = Arrays.asList(user1, user2, user3);
 
        Mockito.when(userRepo.findAll()).thenReturn(users);
 
        mvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    void testGetOneUser() throws Exception { 
        User user = new User("Ayy", "123");
        user.setUserId("1234");

        Mockito.when(userRepo.findById(new AccountId("Ayy", "1234"))).thenReturn(Optional.of(user));
 
        mvc.perform(get("/user/{id}", "Ayy#1234"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId", Matchers.is("1234")))
            .andExpect(jsonPath("$.username", Matchers.is("Ayy")))
            .andExpect(jsonPath("$.password", Matchers.is("123")));
    }

    @Test
    void testCreateUser() throws Exception {
        User user = new User("Ayy", "123");
        user.setUserId("1234");
        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(user);
        Mockito.when(userRepo.save(user)).thenReturn(user);

        mvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonUser)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.is("User created")));
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User("Ayy", "123");
        user.setUserId("1234");
        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(user);
        Mockito.doNothing().when(userRepo).deleteById(new AccountId("Ayy", "1234"));

        mvc.perform(delete("/user/{id}", "Ayy#1234")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonUser)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.is("User deleted")));
    }



}
