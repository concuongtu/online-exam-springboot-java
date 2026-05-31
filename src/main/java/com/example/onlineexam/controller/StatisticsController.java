package com.example.onlineexam.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.ResultRepository;
import com.example.onlineexam.repository.SubjectRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class StatisticsController {

    private final ResultRepository resultRepository;
    private final SubjectRepository subjectRepository;

    public StatisticsController(
            ResultRepository resultRepository,
            SubjectRepository subjectRepository) {

        this.resultRepository = resultRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/statistics")
    public String statistics(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null ||
            !"admin".equals(user.getRole())) {

            return "redirect:/home";
        }

        model.addAttribute(
                "stats",
                resultRepository.getAverageScoreBySubject()
        );

        return "statistics";
    }
}