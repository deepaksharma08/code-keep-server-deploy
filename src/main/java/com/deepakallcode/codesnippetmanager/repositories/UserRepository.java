package com.deepakallcode.codesnippetmanager.repositories;

import com.deepakallcode.codesnippetmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}
