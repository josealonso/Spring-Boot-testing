package com.josealonso.springmvc.controller;

import com.josealonso.springmvc.models.CollegeStudent;
import com.josealonso.springmvc.models.Gradebook;
import com.josealonso.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private Gradebook gradebook;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        //Iterable<CollegeStudent>
        var collegeStudents = studentService.getGradebook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        return "studentInformation";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
        studentService.createStudent(student.getFullName(), student.getLastname(),
                student.getEmailAddress());
        Iterable<CollegeStudent> collegeStudents = studentService.getGradebook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id, Model m) {
        if (!studentService.checkIfStudentIsNull(id)) {
            return "error";
        }
        studentService.deleteStudent(id);
        Iterable<CollegeStudent> collegeStudents = studentService.getGradebook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }
}








