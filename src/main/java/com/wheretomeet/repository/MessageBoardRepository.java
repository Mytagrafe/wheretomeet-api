package com.wheretomeet.repository;

import org.springframework.stereotype.Repository;

import com.wheretomeet.entity.MessageBoard;

import org.springframework.data.repository.CrudRepository;


@Repository
public interface MessageBoardRepository extends CrudRepository<MessageBoard, String> {
    //Spring auto generates methods for us to use, but we can add custom ones if we need it.
}
