package com.alamin.usersecurity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("from User where username=?1")
    User findByUserName(String username);
}
