package com.josealonso.springmvc.repository;

import com.josealonso.springmvc.models.ScienceGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScienceGradesDao extends JpaRepository<ScienceGrade, Integer> {

    public Iterable<ScienceGrade> findGradeByStudentId(int id);
}
