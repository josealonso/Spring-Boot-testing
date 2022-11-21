package com.josealonso.springmvc.repository;

import com.josealonso.springmvc.models.MathGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MathGradesDao extends JpaRepository<MathGrade, Integer> {

    public Iterable<MathGrade> findGradeByStudentId(int id);
}
