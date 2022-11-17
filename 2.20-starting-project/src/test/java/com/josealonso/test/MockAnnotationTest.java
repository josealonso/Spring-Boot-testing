package com.josealonso.test;

import com.josealonso.component.MvcTestingExampleApplication;
import com.josealonso.component.dao.ApplicationDao;
import com.josealonso.component.models.CollegeStudent;
import com.josealonso.component.models.StudentGrades;
import com.josealonso.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    // @Mock
    @MockBean
    private ApplicationDao applicationDao;

    // @InjectMocks
    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        studentOne.setFirstname("Eric");
        studentOne.setLastname("Johnson");
        studentOne.setEmailAddress("eric@scholl.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("When & Verify")
    @Test
    public void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults())).thenReturn(100.00);

        assertEquals(100, applicationService.addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults()), "");

        verify(applicationDao, times(1)).addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults());

    }

    @DisplayName("Find Gpa")
    @Test
    public void assertEqualsTestFinGpa() {
        when(applicationDao.findGradePointAverage(
                studentGrades.getMathGradeResults())).thenReturn(88.31);

        assertEquals(88.31, applicationService.findGradePointAverage(
                studentOne.getStudentGrades().getMathGradeResults()), "");

        verify(applicationDao, times(1)).findGradePointAverage(
                studentGrades.getMathGradeResults());

    }

}
