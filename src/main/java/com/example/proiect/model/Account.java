package com.example.proiect.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
    private int account_id;

    @Column(name = "email", updatable = false)
    private String email;

    @Column(nullable = false)
    private String domain;

    @Column(nullable = false)
    private String password;



}
