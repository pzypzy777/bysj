package com.example.bysj.mapper;

import com.example.bysj.entity.Bracelet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestInfoMapper {

    public Bracelet getTestInfoById(long id);

    public boolean setState();
}