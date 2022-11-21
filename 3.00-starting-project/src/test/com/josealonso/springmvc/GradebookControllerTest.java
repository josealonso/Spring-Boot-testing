package com.josealonso.springmvc;

import com.josealonso.springmvc.models.CollegeStudent;
import com.josealonso.springmvc.models.GradebookCollegeStudent;
import com.josealonso.springmvc.repository.StudentDao;
import com.josealonso.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentCreateServiceMock;

    @Autowired
    private StudentDao studentDao;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Billy");
        request.setParameter("lastname", "Jean");
        request.setParameter("emailAddress", "billy@school.com");
    }

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute("insert into student(id, firstname, lastname, email_address) values " +
                "(10, 'John', 'Smith', 'johnnni@school.com')");
        // jdbc.execute("INSERT INTO student VALUES (?, ?, ?, ?)", 1, "John", "Smith", "john@school.com");
    }

    @Test
    public void getStudentsHttpRequest() throws Exception {
        CollegeStudent studentOne = new GradebookCollegeStudent("Eric", "Rostand",
                "eric@school.com");

        CollegeStudent studentTwo = new GradebookCollegeStudent("Alice", "Rostand",
                "alice@school.com");

        List<CollegeStudent> collegeStudentList = List.of(studentOne, studentTwo);

        when(studentCreateServiceMock.getGradebook()).thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList, studentCreateServiceMock.getGradebook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    public void createStudentHttpRequest() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", request.getParameterValues("firstname"))
                        .param("lastname", request.getParameterValues("lastname"))
                        .param("emailAddress", request.getParameterValues("emailAddress")))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");

        CollegeStudent verifyStudent = studentDao
                .findByEmailAddress("john@school.com");

        assertNotNull(verifyStudent, "Student should be found");

    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("delete from student");
    }

}














