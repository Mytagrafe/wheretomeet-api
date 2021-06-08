package com.wheretomeet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name =  "friends_list")
public class FriendsList implements Serializable {
    @Id
    private String userId;
    private HashSet<User> friends;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public FriendsList() {
        //default constructor
    }

    public FriendsList(String userId) {
        this.userId = userId;
        this.friends = new HashSet<User>();
    }

    public String getFriendsListOwner() {
        return userId;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public boolean removeFriend(User friend) {
        boolean b = friends.remove(friend);
        return b;
    }

    public HashSet<User> getFriends() {
        return friends;
    }
}
