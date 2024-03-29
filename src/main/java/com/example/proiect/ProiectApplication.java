package com.example.proiect;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.security.SecureRandom;

@SpringBootApplication
@ComponentScan
public class ProiectApplication {



	public static void main(String[] args) {
		SpringApplication.run(ProiectApplication.class, args);
	}

}
