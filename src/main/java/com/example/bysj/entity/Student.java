package com.example.bysj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Student {
    private long id;

    private long bracelet_id;

    private String name;

    private Integer sex;

    private long class_id;

    private Date physical_time;

}
