package com.example.bysj.mapper;

import com.example.bysj.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassroomInfoMapper {
    public Classroom getClassroomById(long id);
}
