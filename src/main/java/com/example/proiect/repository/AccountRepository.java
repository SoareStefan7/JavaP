package com.example.proiect.repository;

import com.example.proiect.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository  extends JpaRepository<Account,String> {
    List<Account> findByEmail(String email);
}
