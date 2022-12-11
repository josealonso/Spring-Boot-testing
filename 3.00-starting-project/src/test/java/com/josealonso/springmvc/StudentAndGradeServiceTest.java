package com.josealonso.springmvc;

import com.josealonso.springmvc.models.*;
import com.josealonso.springmvc.repository.HistoryGradesDao;
import com.josealonso.springmvc.repository.MathGradesDao;
import com.josealonso.springmvc.repository.ScienceGradesDao;
import com.josealonso.springmvc.repository.StudentDao;
import com.josealonso.springmvc.service.StudentAndGradeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestPropertySource("/application-test.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradeDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    private HistoryGradesDao historyGradeDao;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }

    @Test
    public void createStudentService() {
        studentService.createStudent("John2", "Smith2",
                "john2@school.com");

        CollegeStudent student = studentDao.
                findByEmailAddress("john2@school.com");

        log.info("============= SERVICE TEST: right after findByEmailAddress(); ===========");
        assertEquals("john2@school.com",
                student.getEmailAddress(), "find by email");
    }

    @Test
    public void isStudentNullCheck() {

        // This assertion fails if the database is not populated
        assertTrue(studentService.checkIfStudentIsNull(1));

        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService() {
        Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);
        Optional<MathGrade> deletedMathGrade = mathGradeDao.findById(1);
        Optional<HistoryGrade> deletedHistoryGrade = historyGradeDao.findById(1);
        Optional<ScienceGrade> deletedScienceGrade = scienceGradeDao.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(), "Return true");
        assertTrue(deletedMathGrade.isPresent(), "Return True");
        assertTrue(deletedScienceGrade.isPresent(), "Return True");
        assertTrue(deletedHistoryGrade.isPresent(), "Return True");

        studentService.deleteStudent(1);

        deletedCollegeStudent = studentDao.findById(1);
        deletedMathGrade = mathGradeDao.findById(1);
        deletedScienceGrade = scienceGradeDao.findById(1);
        deletedHistoryGrade = historyGradeDao.findById(1);

        assertFalse(deletedCollegeStudent.isPresent());
        assertFalse(deletedMathGrade.isPresent());
        assertFalse(deletedScienceGrade.isPresent());
        assertFalse(deletedHistoryGrade.isPresent());

    }

    @Sql("/insertData.sql")
    @Test
    public void getGradeBookService() {
        Iterable<CollegeStudent> iterableCollegeStudents = studentService.getGradebook();

        List<CollegeStudent> collegeStudents = new ArrayList<>();

        // Convert an Iterable to a List
        for (CollegeStudent collegeStudent : iterableCollegeStudents) {
            collegeStudents.add(collegeStudent);
        }

        assertEquals(2, collegeStudents.size());
    }

    @Test
    public void createGradeService() {
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        assertTrue(studentService.createGrade(80.50, 1, "science"));
        assertTrue(studentService.createGrade(80.50, 1, "history"));

        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(1);

        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2,
                "Student should have math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2,
                "Student should have science grades");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2,
                "Student should have history grades");
    }

    @Test
    public void createGradeServiceReturnFalse() {
        int valueOutOfRange = 105;
        int negativeValue = -5;
        int invalidStudentId = 2;
        String invalidSubject = "literature";
        assertFalse(studentService.createGrade(valueOutOfRange, 1, "math"));
        assertFalse(studentService.createGrade(negativeValue, 1, "math"));
        assertFalse(studentService.createGrade(80.50, invalidStudentId, "math"));
        assertFalse(studentService.createGrade(80.50, 1, invalidSubject));
    }

    @Test
    public void deleteGradesService() {
        assertEquals(1, studentService.deleteGrade(1, "math"),
                "Should return student id after delete");
        assertEquals(1, studentService.deleteGrade(1, "science"),
                "Should return student id after delete");
        assertEquals(1, studentService.deleteGrade(1, "history"),
                "Should return student id after delete");
    }

    @Test
    public void deleteGradeServiceReturnStudentIdOfZero() {
        assertEquals(0, studentService.deleteGrade(0, "science"),
                "No student should have 0 id");
        assertEquals(0, studentService.deleteGrade(1, "literature"),
                "No student should have a literature class");
    }

    @Test
    public void studentInformation() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(1);

        assertNotNull(gradebookCollegeStudent);
        assertEquals(1, gradebookCollegeStudent.getId());
        assertEquals("John", gradebookCollegeStudent.getFirstname());
        assertEquals("Smith", gradebookCollegeStudent.getLastname());
        assertEquals("john@school.com", gradebookCollegeStudent.getEmailAddress());
        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);
    }

    @Test
    public void studentInformationServiceAndReturnNull() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0);
        assertNull(gradebookCollegeStudent);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }

}
