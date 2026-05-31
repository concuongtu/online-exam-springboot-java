package com.example.onlineexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.onlineexam.entity.Subject;
import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.SubjectRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "admin".equals(user.getRole());
    }

    @GetMapping("/subjects")
    public String subjects(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("subject", new Subject());

        return "subjects";
    }

    @PostMapping("/subjects/add")
    public String addSubject(
            @ModelAttribute Subject subject,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        subjectRepository.save(subject);

        return "redirect:/subjects";
    }

    @GetMapping("/subjects/delete/{id}")
    public String deleteSubject(
            @PathVariable Integer id,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        subjectRepository.deleteById(id);

        return "redirect:/subjects";
    }
}