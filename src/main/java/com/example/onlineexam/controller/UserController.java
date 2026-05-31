package com.example.onlineexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "admin".equals(user.getRole());
    }

    @GetMapping("/users")
    public String users(@RequestParam(required = false) String keyword,
                        Model model,
                        HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute(
                    "users",
                    userRepository
                            .findByUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCase(
                                    keyword,
                                    keyword
                            )
            );
        } else {
            model.addAttribute(
                    "users",
                    userRepository.findAll()
            );
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("userForm", new User());

        return "users";
    }

    @PostMapping("/users/add")
    public String addUser(
            @ModelAttribute("userForm") User user,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(
            @PathVariable Integer id,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        userRepository.deleteById(id);

        return "redirect:/users";
    }
    @GetMapping("/users/edit/{id}")
    public String editUserPage(@PathVariable Integer id,
                               Model model,
                               HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        User userEdit = userRepository.findById(id).orElse(null);

        if (userEdit == null) {
            return "redirect:/users";
        }

        model.addAttribute("userEdit", userEdit);

        return "edit-user";
    }

    @PostMapping("/users/edit")
    public String updateUser(@ModelAttribute("userEdit") User userEdit,
                             HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        userRepository.save(userEdit);

        return "redirect:/users";
    }
}