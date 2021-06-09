package com.wheretomeet.repository;

import com.wheretomeet.model.GroupsList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsListRepository extends CrudRepository<GroupsList, String> {
    // Spring auto generates methods for us to use, but we can add custom ones if we need it.
}
