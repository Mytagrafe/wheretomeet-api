package com.wheretomeet.controller;

import java.util.ArrayList;
import java.util.List;

import com.wheretomeet.entity.MessageBoard;
import com.wheretomeet.model.Message;
import com.wheretomeet.repository.MessageBoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
public class MessageBoardController {
    final static Logger log = LoggerFactory.getLogger(MessageBoardController.class);

    @Autowired
    MessageBoardRepository messagesRepo;

    @GetMapping("/messageboard/{gid}")
    public ResponseEntity<?> getMessages(@PathVariable("gid") String gid) {

        MessageBoard msgBoard = messagesRepo.findById(gid).orElse(null);

        if(msgBoard != null) {
            return new ResponseEntity<ArrayList<Message>>(msgBoard.getMessages(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/messageboard/{gid}/recentMessages")
    public ResponseEntity<?> getRecentMessages(@PathVariable("gid") String gid) {

        MessageBoard msgBoard = messagesRepo.findById(gid).orElse(null);

        if(msgBoard != null) {
            return new ResponseEntity<List<Message>>(msgBoard.getRecentMessages(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/messageboard/{gid}/save")
    public ResponseEntity<?> storeMessage(@PathVariable("gid") String gid, @RequestBody Message message) {

        MessageBoard msgBoard = messagesRepo.findById(gid).orElse(null);

        if(msgBoard != null) {
            boolean added = msgBoard.addMessage(message);
            if(!added) {
                new ResponseEntity<String>("cannot store message", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            messagesRepo.save(msgBoard);
            return new ResponseEntity<String>("message stored successfully", HttpStatus.OK);
        }

        return new ResponseEntity<String>("cannot store message", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
