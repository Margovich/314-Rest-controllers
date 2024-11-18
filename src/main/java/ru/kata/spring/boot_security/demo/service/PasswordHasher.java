package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        String rawPassword = "12345";
        String inMemorypass = "pass";


        String hashedPassword = passwordEncoder.encode(rawPassword);
        String hashedinMemory = passwordEncoder.encode(inMemorypass);


        System.out.println("Хэш пароля для базы данных: " + hashedPassword);
        System.out.println("Хэш пароля inMemory: " + hashedinMemory);


    }
}
