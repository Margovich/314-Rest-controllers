package ru.kata.spring.boot_security.demo;

import ru.kata.spring.boot_security.demo.model.Role;

public class main {
    public static void main(String[] args) {
        Role role = new Role("admin");
        Role role2 = new Role("user");
        System.out.println(role);
        System.out.println(role2);


    }
}
