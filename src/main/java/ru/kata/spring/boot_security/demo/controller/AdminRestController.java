package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser (@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @GetMapping()
    public ResponseEntity<User> createUser (@Valid @RequestBody User user) {
        userService.saveUser(user);
        User createdUser = userService.findByNickname(user.getUsername());
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping ("/{id}")
    public User updateUser(@ModelAttribute("user") @Valid User user, @PathVariable("id") Long id, BindingResult bindingResult) {
        user.setId(id);
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(id);
    }

}
