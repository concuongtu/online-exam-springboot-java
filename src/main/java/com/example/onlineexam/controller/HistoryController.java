package com.example.onlineexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.ResultRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HistoryController {

    private final ResultRepository resultRepository;

    public HistoryController(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @GetMapping("/history")
    public String history(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if(user == null ||
           !user.getRole().equals("admin")) {

            return "redirect:/home";
        }

        model.addAttribute(
                "results",
                resultRepository.findAllByOrderByIdDesc()
        );

        return "history";
    }
}