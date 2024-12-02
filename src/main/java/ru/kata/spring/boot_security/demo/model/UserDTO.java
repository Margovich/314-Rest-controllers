package ru.kata.spring.boot_security.demo.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.kata.spring.boot_security.demo.validation.CreateGroup;
import ru.kata.spring.boot_security.demo.validation.UpdateGroup;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Nickname cannot be empty", groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё0-9_-]{3,20}$", message = "Nickname can only contain letters, numbers, underscores and dashes and from 3 to 20 characters", groups = {CreateGroup.class, UpdateGroup.class})
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @NotBlank(message = "Password cannot be empty", groups = {CreateGroup.class})
    @Size(min = 6, message = "Password must be at least 6 characters long", groups = {CreateGroup.class, UpdateGroup.class})
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Email cannot be empty", groups = {CreateGroup.class, UpdateGroup.class})
    @Email(message = "Email should be valid", groups = {CreateGroup.class, UpdateGroup.class})
    @Column(name = "email")
    private String email;

    @Min(value = 1, message = "Age must be greater than 0", groups = {CreateGroup.class, UpdateGroup.class})
    @Column(name = "age")
    private int age;

    @NotEmpty(message = "At least one role must be selected", groups = {CreateGroup.class, UpdateGroup.class})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;


    public UserDTO(Long id, String nickname, String password, String email, int age, Set<Role> roles) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.age = age;
        this.roles = roles;


    }

    public UserDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }


}

