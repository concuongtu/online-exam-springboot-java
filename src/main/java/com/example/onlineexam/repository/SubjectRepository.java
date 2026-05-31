package com.example.onlineexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlineexam.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}