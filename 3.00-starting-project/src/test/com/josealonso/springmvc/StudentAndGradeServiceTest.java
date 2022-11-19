package com.josealonso.springmvc;

import com.josealonso.springmvc.models.CollegeStudent;
import com.josealonso.springmvc.repository.StudentDao;
import com.josealonso.springmvc.service.StudentAndGradeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    public void setupDatabase() {
        // int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM EMPLOYEE", Integer.class);

        jdbc.update("INSERT INTO student VALUES (?, ?, ?, ?)", 1, "John", "Smith", "john@school.com");
        // jdbc.execute("insert into student(id, firstname, lastname, email_address) " +   // This SQL statement does not work !!
        //      "values (1, 'John', 'Smith', 'john@school.com')");
    }

    @Test
    public void createStudentService() {
        studentService.createStudent("John", "Smith",
                "john@school.com");

        CollegeStudent student = studentDao.
                findByEmailAddress("john@school.com");

        log.info("============= SERVICE TEST: right after findByEmailAddress(); ===========");
        assertEquals("john@school.com",
                student.getEmailAddress(), "find by email");
    }

    @Test
    public void isStudentNullCheck() {

        // This assertion fails if the database is not populated
        assertTrue(studentService.checkIfStudentIsNull(1));

        assertFalse(studentService.checkIfStudentIsNull(0));
    }

/*    @Test
    public void deleteStudentService() {
        Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);
        assertTrue(deletedCollegeStudent.isPresent(), "Return true");
        studentService.deleteStudent(1);
        deletedCollegeStudent = studentDao.findById(1);
        assertFalse(deletedCollegeStudent.isPresent(), "Return false");
    } */

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

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("delete from student");
    }

}
