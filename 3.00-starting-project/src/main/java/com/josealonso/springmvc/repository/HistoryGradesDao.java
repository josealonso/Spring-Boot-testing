package com.josealonso.springmvc.repository;

import com.josealonso.springmvc.models.HistoryGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryGradesDao extends JpaRepository<HistoryGrade, Integer> {
    public Iterable<HistoryGrade> findGradeByStudentId(int id);

    public void deleteByStudentId(int id);
}
