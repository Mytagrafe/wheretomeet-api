package com.wheretomeet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.wheretomeet.model.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, String> {
    //Spring auto generates methods for us to use, but we can add custom ones if we need it.
}