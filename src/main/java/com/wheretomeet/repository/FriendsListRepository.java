package com.wheretomeet.repository;

import com.wheretomeet.entity.FriendsList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsListRepository extends CrudRepository<FriendsList, String> {
    //Spring auto generates methods for us to use, but we can add custom ones if we need it.
}
