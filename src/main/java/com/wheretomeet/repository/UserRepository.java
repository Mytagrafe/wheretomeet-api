package com.wheretomeet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wheretomeet.model.AccountId;
import com.wheretomeet.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, AccountId> {
    //Spring auto generates methods for us to use, but we can add custom ones if we need it.
}