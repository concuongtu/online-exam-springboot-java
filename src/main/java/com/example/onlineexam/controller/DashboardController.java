package com.example.onlineexam.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.onlineexam.entity.Result;
import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.ResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    private final ResultRepository resultRepository;

    public DashboardController(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) throws Exception {

        User user = (User) session.getAttribute("user");

        if (user == null || !"admin".equals(user.getRole())) {
            return "redirect:/home";
        }

        List<Result> results = resultRepository.findAll();

        ObjectMapper mapper = new ObjectMapper();

        model.addAttribute("labelsJson",
                mapper.writeValueAsString(
                        results.stream()
                                .map(r -> "Lần " + r.getId())
                                .toList()
                ));

        model.addAttribute("scoresJson",
                mapper.writeValueAsString(
                        results.stream()
                                .map(Result::getScore)
                                .toList()
                ));

        return "dashboard";
    }
}