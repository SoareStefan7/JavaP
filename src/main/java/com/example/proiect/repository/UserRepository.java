package com.example.proiect.repository;

import com.example.proiect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findUserByEmail(String user_email);
}
