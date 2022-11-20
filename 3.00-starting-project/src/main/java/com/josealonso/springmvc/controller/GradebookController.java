package com.josealonso.springmvc.controller;

import com.josealonso.springmvc.models.Gradebook;
import com.josealonso.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        // return (String) m.getAttribute("students");
    }

    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        return "studentInformation";
    }

}
