package com.wheretomeet.entity;

import java.io.Serializable;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.wheretomeet.model.LiteUser;

@Entity
@Table(name =  "friends_list")
public class FriendsList implements Serializable {
    @Id
    private String userId;
    private HashSet<LiteUser> friends;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public FriendsList() {
        this.friends = new HashSet<LiteUser>();
    }

    public FriendsList(String userId) {
        this.userId = userId;
        this.friends = new HashSet<LiteUser>();
    }

    public String getFriendsListOwner() {
        return userId;
    }

    public boolean addFriend(LiteUser friend) {
        if(friends == null) {
            friends = new HashSet<>();
        }
        if(friends.contains(friend)) {
            return true;
        }
        return friends.add(friend);
    }

    public boolean removeFriend(LiteUser friend) {
        if(friends == null) {
            throw new NullPointerException("user's friends list is null");
        }
        return friends.remove(friend);
    }

    public HashSet<LiteUser> getFriends() {
        return friends;
    }
}
