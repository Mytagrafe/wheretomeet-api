package com.wheretomeet.controller;

import java.util.Optional;

import com.wheretomeet.entity.MessageBoard;
import com.wheretomeet.model.Message;
import com.wheretomeet.repository.MessageBoardRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageBoardController.class)
public class MessageBoardControllerTests {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    MessageBoardRepository messagesRepo;

    private MessageBoard msgBoard;
    private MessageBoard smallMsgBoard;

    @BeforeEach
    void initMessageBoard() {
        msgBoard = new MessageBoard("123456789");
        smallMsgBoard = new MessageBoard("987654321");
        for(int i = 0; i < 10; i++) {
            String msg = "I am msg " + i;
            String fromUser = "U-" + (i % 3);
            long timestamp  = i;
            msgBoard.addMessage(new Message(msg, fromUser, timestamp));

            // allocate 5 messages to the smaller message board for testing getting recent messages.
            if(i > 4) {
                smallMsgBoard.addMessage(new Message(msg, fromUser, timestamp));
            }
        }
    }

    @Test
    void testGetMessages() throws Exception {
        Mockito.when(messagesRepo.findById("123456789")).thenReturn(Optional.of(msgBoard));
        
        mvc.perform(get("/messageboard/{gid}", "123456789"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(10)));
    }

    @Test
    void testStoreMessages() throws Exception {
        Mockito.when(messagesRepo.findById("123456789")).thenReturn(Optional.of(msgBoard));
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(new Message("I am new message", "U-4", 100));

        mvc.perform(put("/messageboard/{gid}/save", "123456789")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonMessage)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.is("message stored successfully")));
    }

    @Test
    void testGetRecentMessages() throws Exception {
        Mockito.when(messagesRepo.findById("123456789")).thenReturn(Optional.of(msgBoard));

        //now there are 11 messages, so I expect recent messages to return 10 only.
        msgBoard.addMessage(new Message("I am new message", "U-4", 100));

        mvc.perform(get("/messageboard/{gid}/recentMessages", "123456789"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(10)));
    }

    @Test
    void testGetRecentMessagesOnSmallBoard() throws Exception {
        Mockito.when(messagesRepo.findById("987654321")).thenReturn(Optional.of(smallMsgBoard));

        mvc.perform(get("/messageboard/{gid}/recentMessages", "987654321"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(5)));
    }
}
