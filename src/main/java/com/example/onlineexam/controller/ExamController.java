package com.example.onlineexam.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.onlineexam.entity.Question;
import com.example.onlineexam.entity.Subject;
import com.example.onlineexam.repository.QuestionRepository;
import com.example.onlineexam.repository.SubjectRepository;

import com.example.onlineexam.entity.Result;
import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.ResultRepository;
import jakarta.servlet.http.HttpSession;
@Controller
public class ExamController {

    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;

    public ExamController(SubjectRepository subjectRepository,
            QuestionRepository questionRepository,
            ResultRepository resultRepository) {
this.subjectRepository = subjectRepository;
this.questionRepository = questionRepository;
this.resultRepository = resultRepository;
}

    @GetMapping("/select-subject")
    public String selectSubject(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        model.addAttribute("subjects", subjectRepository.findAll());
        return "select-subject";
    }

    @GetMapping("/exam/{subjectId}")
    public String exam(@PathVariable Integer subjectId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        if (subject == null) return "redirect:/select-subject";
        List<Question> questions = questionRepository.findBySubject(subject);
        model.addAttribute("user", user);
        model.addAttribute("subject", subject);
        model.addAttribute("questions", questions);
        return "exam";
    }

    @PostMapping("/submit-exam")
    public String submitExam(@RequestParam Integer subjectId,
                             @RequestParam Map<String, String> params,
                             HttpSession session,
                             Model model) {

        Subject subject =
                subjectRepository.findById(subjectId)
                        .orElse(null);

        if (subject == null) {
            return "redirect:/select-subject";
        }

        List<Question> questions =
                questionRepository.findBySubject(subject);

        int score = 0;

        for (Question q : questions) {

            String userAnswer =
                    params.get("question_" + q.getId());

            if (userAnswer != null &&
                    userAnswer.equals(q.getCorrectAnswer())) {
                score++;
            }
        }

        User user = (User) session.getAttribute("user");

        if (user != null) {
            Result result = new Result();

            result.setUsername(user.getUsername());
            result.setSubjectId(subjectId);
            result.setScore(score);
            result.setTotalQuestions(questions.size());

            resultRepository.save(result);
        }

        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());

        return "result";
    }
}