package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public AdminController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "/user")
    public String showUserInfo(Model model, Principal principal) {
        User user = userRepository.findUserByNickname(principal.getName());
        model.addAttribute("user", user);
        return "user";

    }

    @GetMapping("/adduser")
    public String openUserAddForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", userService.getAllRoles());
        return "adduser";
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute(value = "user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/updateuser/{id}")
    public String openUpdateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("role", userService.getAllRoles());
        return "updateuser";
    }

    @PostMapping("/updateuser/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteuser")
    public String delete(@RequestParam(value = "id", required = false) Long id) {
        userService.deleteUser(id);

        return "redirect:/admin";

    }
}
