package com.example.proiect.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String sub;
    private boolean email_verified;
    private Date updated_at;
    private String nikName;
    private String name;


}