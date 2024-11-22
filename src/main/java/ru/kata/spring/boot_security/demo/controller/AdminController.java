package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validation.CreateGroup;
import ru.kata.spring.boot_security.demo.validation.UpdateGroup;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("usersList", userService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("role", userService.getAllRoles());
        return "admin";
    }

    @PostMapping("")
    public String addUser(@ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("role", userService.getAllRoles());
            return "admin";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, @PathVariable("id") Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping ("/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
