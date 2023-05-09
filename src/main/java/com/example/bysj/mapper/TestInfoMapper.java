package com.example.bysj.mapper;

import com.example.bysj.entity.Bracelet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestInfoMapper {

    public Bracelet getTestInfoById(long id);

    public boolean setState();
    public boolean setStateZC();
    public String checkState();

    public void updateTestInfoById(long id,int blood_oxygen,int max_heart_rate,int min_heart_rate,int avg_heart_rate,int now_heart_rate);
}