package com.wheretomeet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "message_boards")
public class MessageBoard implements Serializable {

    private final int RECENT_MESSAGE_COUNT = 10;

    @Id
    String groupId;
    ArrayList<Message> messages;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "groupId", referencedColumnName = "groupId")
    Group group;
    
    public MessageBoard() {
        this.messages = new ArrayList<>();
    }

    public MessageBoard(String groupId) {
        this.groupId = groupId;
        this.messages = new ArrayList<>();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public List<Message> getRecentMessages() {
        if(this.messages == null) {
            throw new NullPointerException("messages board is null.");
        }
        int numMessages = messages.size();
        if(numMessages <= RECENT_MESSAGE_COUNT) {
            return messages;
        }
        return messages.subList(messages.size()-RECENT_MESSAGE_COUNT, messages.size());
    }

    public boolean addMessage(Message message) {
        if(this.messages == null) {
            this.messages = new ArrayList<>();
        }
        return messages.add(message);
    }

    public boolean deleteMessage(Message message) {
        if(this.messages == null) {
            throw new NullPointerException("messages board is null.");
        }
        return messages.remove(message);
    }
}
