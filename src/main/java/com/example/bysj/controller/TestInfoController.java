package com.example.bysj.controller;

import com.example.bysj.entity.Bracelet;
import com.example.bysj.entity.Student;
//import com.example.bysj.service.StudentInfoService;
//import com.example.bysj.service.TestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("TestInfo")
public class TestInfoController {
//    @Autowired
//    private TestInfoService testInfoService;
//
//    @Autowired
//    private StudentInfoService studentInfoService;

    @GetMapping("get")
    public Bracelet getTestInfo(@RequestParam("id") long id){
//        Student student = studentInfoService.getStudentInfoById(id);
//        return  testInfoService.getTestInfo(student.getId());
        return null;
    }
}
