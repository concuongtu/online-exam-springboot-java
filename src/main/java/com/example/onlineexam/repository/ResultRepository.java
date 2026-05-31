package com.example.onlineexam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.onlineexam.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {

    List<Result> findAllByOrderByIdDesc();

    @Query("""
    		SELECT r.subjectId,
    		       AVG(r.score)
    		FROM Result r
    		GROUP BY r.subjectId
    		""")
    		List<Object[]> getAverageScoreBySubject();
}
