package ru.kata.spring.boot_security.demo.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.validation.CreateGroup;
import ru.kata.spring.boot_security.demo.validation.UpdateGroup;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {

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

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @NotEmpty(message = "At least one role must be selected", groups = {CreateGroup.class, UpdateGroup.class})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    public User() {}

    public User(String nickname, String password, String email, int age, Set<Role> roles) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.age = age;
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be empty") String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override

    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return id + " " + age + " " + email + " " + nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age &&
                Objects.equals(nickname, user.nickname) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, age);
    }
}
