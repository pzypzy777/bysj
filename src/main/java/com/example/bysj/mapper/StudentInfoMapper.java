package com.example.bysj.mapper;

import com.example.bysj.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface StudentInfoMapper {

    public Student getStudentById(long id);

    public List<Student> getAllStudents();

}
