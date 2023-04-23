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

    public List<Student> getStudentsBySex(long sex);

    public List<Student> getStudentsByName(String name);

    public Boolean bindBraceletId(long id);
}
