package com.example.bysj.controller;

import com.example.bysj.entity.Bracelet;
import com.example.bysj.entity.Student;
import com.example.bysj.mapper.StudentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @GetMapping("/get")
    @ResponseBody
    public List<Student> getStudentList(){
        List<Student> list = studentInfoMapper.getAllStudents();
        return list;
    }
    @GetMapping("/getbyid")
    @ResponseBody
    public Student getStudentById(@RequestParam("id") long id){
        Student student = studentInfoMapper.getStudentById(id);
        return student;
    }
}
