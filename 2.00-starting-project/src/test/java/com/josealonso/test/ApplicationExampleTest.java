package com.josealonso.test;

import com.josealonso.component.MvcTestingExampleApplication;
import com.josealonso.component.models.CollegeStudent;
import com.josealonso.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {

    private static int count = 0;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.school.name}")
    private String schoolName;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void beforeEach() {
        count = count + 1;
        System.out.println("Testing: " + appInfo + " which is " + appDescription +
                " Version: " + appVersion + ". Execution of test method " + count);
        student.setFirstname("Alice");
        student.setLastname("Johansson");
        student.setEmailAddress("alice@school.com");
        studentGrades.setMathGradeResults(List.of(100.0, 85.0, 76.50, 91.75));
        student.setStudentGrades(studentGrades);
    }

    @Test
    void basicTest() {

    }
}
