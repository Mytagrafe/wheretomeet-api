package com.wheretomeet.controller;

import com.wheretomeet.model.Message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Controller
public class MessageController {

    final static Logger log = LoggerFactory.getLogger(MessageController.class);

    // Handles messages from /app/chat. (Note the Spring adds the /app prefix for us).
    @MessageMapping("/chat")
    // Sends the return value of this method to /topic/messages
    @SendTo("/topic/messages")
    public Message getMessages(Message msg){
        return msg;
    }

}