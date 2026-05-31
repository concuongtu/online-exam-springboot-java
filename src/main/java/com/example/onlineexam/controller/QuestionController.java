package com.example.onlineexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.onlineexam.entity.Question;
import com.example.onlineexam.entity.Subject;
import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.QuestionRepository;
import com.example.onlineexam.repository.SubjectRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final SubjectRepository subjectRepository;

    public QuestionController(
            QuestionRepository questionRepository,
            SubjectRepository subjectRepository) {

        this.questionRepository = questionRepository;
        this.subjectRepository = subjectRepository;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "admin".equals(user.getRole());
    }

    @GetMapping("/questions")
    public String questions(@RequestParam(required = false) String keyword,
                            Model model,
                            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("questions",
                    questionRepository.findByQuestionTextContainingIgnoreCase(keyword));
        } else {
            model.addAttribute("questions",
                    questionRepository.findAll());
        }

        model.addAttribute("keyword", keyword);

        return "questions";
    }

    @GetMapping("/questions/add")
    public String addQuestionPage(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        model.addAttribute("question", new Question());
        model.addAttribute("subjects", subjectRepository.findAll());

        return "add-question";
    }

    @PostMapping("/questions/add")
    public String addQuestion(
            @ModelAttribute Question question,
            @RequestParam Integer subjectId,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        Subject subject =
                subjectRepository.findById(subjectId)
                        .orElse(null);

        question.setSubject(subject);

        questionRepository.save(question);

        return "redirect:/questions";
    }

    @GetMapping("/questions/delete/{id}")
    public String deleteQuestion(
            @PathVariable Integer id,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        questionRepository.deleteById(id);

        return "redirect:/questions";
    }
        @GetMapping("/questions/edit/{id}")
        public String editQuestionPage(
                @PathVariable Integer id,
                Model model,
                HttpSession session) {

            if (!isAdmin(session)) {
                return "redirect:/home";
            }

            Question question =
                    questionRepository.findById(id)
                            .orElse(null);

            if (question == null) {
                return "redirect:/questions";
            }

            model.addAttribute("question", question);
            model.addAttribute("subjects", subjectRepository.findAll());

            return "edit-question";
        }

        @PostMapping("/questions/edit")
        public String updateQuestion(
                @ModelAttribute Question question,
                @RequestParam Integer subjectId,
                HttpSession session) {

            if (!isAdmin(session)) {
                return "redirect:/home";
            }

            Subject subject =
                    subjectRepository.findById(subjectId)
                            .orElse(null);

            question.setSubject(subject);

            questionRepository.save(question);

            return "redirect:/questions";
        
    }
}