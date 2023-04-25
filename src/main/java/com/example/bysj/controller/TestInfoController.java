package com.example.bysj.controller;

import com.example.bysj.entity.Bracelet;
import com.example.bysj.mapper.TestInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TestInfo")
@CrossOrigin
public class TestInfoController {
    @Autowired
    private TestInfoMapper testInfoMapper;

    @GetMapping("/getbyid")
    @ResponseBody
    public Bracelet getTestInfo(@RequestParam("id") long id){
        return testInfoMapper.getTestInfoById(id);
    }

}
