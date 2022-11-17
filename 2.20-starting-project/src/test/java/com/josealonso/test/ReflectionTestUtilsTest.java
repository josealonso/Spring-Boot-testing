package com.josealonso.test;

import com.josealonso.component.MvcTestingExampleApplication;
import com.josealonso.component.models.CollegeStudent;
import com.josealonso.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ReflectionTestUtilsTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void studentBeforeEach() {
        // studentOne = new CollegeStudent("Brian", "Thel", "brian@chool.com");
        studentOne.setFirstname("Brian");
        studentOne.setLastname("Thel");
        studentOne.setEmailAddress("brian@school.com");
        studentOne.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(studentOne, "id", 1);
        ReflectionTestUtils.setField(studentOne, "studentGrades",
                new StudentGrades(List.of(100.0, 85.0, 76.50, 91.75)));
    }

    @Test
    public void getPrivateField() {
        assertEquals(1, ReflectionTestUtils.getField(studentOne, "id"));
    }

    @Test
    public void invokePrivateMethod() {
        assertEquals("Brian 1",
        ReflectionTestUtils.invokeMethod(studentOne, "getFirstNameAndId"),
                "Fail, private method not called");
    }

}



