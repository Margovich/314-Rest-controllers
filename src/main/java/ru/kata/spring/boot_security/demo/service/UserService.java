package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService extends UserDetailsService {
    //User
    User saveUser(@Valid User user);
    User updateUser(Long id, @Valid User updatedUser);
    void deleteUser(long id);
    User getUser(long id);
    List<User> getAllUsers();
    User findByNickname(String nickname);
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    //Role
    List<Role> getAllRoles();
    void saveRole(Role role);
    Role showRoleById(Long id);
}
