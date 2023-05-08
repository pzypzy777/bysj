package com.example.bysj.controller;

import com.example.bysj.entity.Bracelet;
import com.example.bysj.entity.Student;
import com.example.bysj.mapper.StudentInfoMapper;
import com.example.bysj.service.RedisTemplateService;
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

    @Autowired
    private RedisTemplateService redisTemplateService;

    @GetMapping("/get")
    @ResponseBody
    public List<Student> getStudentList(){
        List<Student> list = studentInfoMapper.getAllStudents();

//        redisTemplateService.set("stu",list);
        return list;
    }
    @GetMapping("/getbyid")
    @ResponseBody
    public List<Student> getStudentById(@RequestParam("id") long id){
        List<Student> list =  studentInfoMapper.getStudentById(id);
        return list;
    }

    @GetMapping("/getbysex")
    @ResponseBody
    public List<Student> getStudentBySex(@RequestParam("sex") long sex){
        List<Student> list = studentInfoMapper.getStudentsBySex(sex);
        return list;
    }

    @GetMapping("/getbyname")
    @ResponseBody
    public List<Student> getStudentByName(@RequestParam("name") String name){
        List<Student> list = studentInfoMapper.getStudentsByName(name);
        return list;
    }
}
