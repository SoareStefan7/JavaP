package com.example.proiect.service;


import com.example.proiect.model.Account;
import com.example.proiect.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountByEmail(String userEmail) {
        return accountRepository.findByEmail(userEmail);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(String user_email) {
        accountRepository.deleteById(user_email);
    }
}
