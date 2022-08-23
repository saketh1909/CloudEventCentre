package com.cmpe275.term.repository;

import com.cmpe275.term.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("users")
public interface UsersRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);

    public User findById(long id);

    public User findByscreenName(String screenName);
}
