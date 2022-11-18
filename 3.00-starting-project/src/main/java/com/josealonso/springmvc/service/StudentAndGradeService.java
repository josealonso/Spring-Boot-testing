package com.josealonso.springmvc.service;

import com.josealonso.springmvc.models.CollegeStudent;
import com.josealonso.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    @PostMapping("/student")
    public void createStudent(String firstName, String lastName, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstName, lastName,emailAddress);
        student.setId(0);
        studentDao.save(student);
    }
}
