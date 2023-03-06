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
public class StudentController {
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @GetMapping("/get")
    @ResponseBody
    public Object getStudentList(){
        List<HashMap<String, Object>> list = studentInfoMapper.getAllStudents();
        System.out.println("666");
        return list;
    }
}
