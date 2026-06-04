package com.example.onlineexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.UserRepository;
import com.example.onlineexam.repository.SubjectRepository;
import com.example.onlineexam.repository.QuestionRepository;
import com.example.onlineexam.repository.ResultRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;

    public LoginController(UserRepository userRepository,
                           SubjectRepository subjectRepository,
                           QuestionRepository questionRepository,
                           ResultRepository resultRepository) {

        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.questionRepository = questionRepository;
        this.resultRepository = resultRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/home";
        }

        model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        if ("admin".equals(user.getRole())) {
            model.addAttribute("totalUsers", userRepository.count());
            model.addAttribute("totalSubjects", subjectRepository.count());
            model.addAttribute("totalQuestions", questionRepository.count());
            model.addAttribute("totalResults", resultRepository.count());
        }

        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ========== ĐỔI MẬT KHẨU ==========
    @GetMapping("/change-password")
    public String changePasswordPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        // ✅ THÊM DÒNG NÀY để sidebar nhận biết quyền admin/user
        model.addAttribute("user", user);
        model.addAttribute("activePage", "password");
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 Model model) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        // ✅ THÊM DÒNG NÀY (cần thiết để hiển thị sidebar đúng khi có lỗi hoặc thành công)
        model.addAttribute("user", user);

        if (!user.getPassword().equals(oldPassword)) {
            model.addAttribute("error", "Mật khẩu cũ không đúng");
            return "change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới không khớp");
            return "change-password";
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        session.setAttribute("user", user);
        model.addAttribute("success", "Đổi mật khẩu thành công");

        return "change-password";
    }
}