package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDTO;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/thisUser")
    public ResponseEntity<User> getThisUser(Principal principal) {
        User user = userService.findByNickname(principal.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        Set<Role> roles = user.getRoles();
        UserDTO userDTO = new UserDTO(user.getId(), user.getNickname(), user.getPassword(), user.getEmail(), user.getAge(), roles);
        return ResponseEntity.ok(userDTO);
    }


    @GetMapping
    public ResponseEntity<User> createUser (@Valid @RequestBody User user) {
        userService.saveUser(user);
        User createdUser = userService.findByNickname(user.getUsername());
        return ResponseEntity.ok(createdUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = userService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

}
