package com.example.onlineexam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlineexam.entity.Question;
import com.example.onlineexam.entity.Subject;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findBySubject(Subject subject);

    List<Question> findByQuestionTextContainingIgnoreCase(String keyword);
}