package com.wheretomeet.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.wheretomeet.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    //Spring auto generates methods for us to use, but we can add custom ones if we need it.
    @Query("select u from User u where email=?1")
    Optional<User> findByEmail(String email);
}