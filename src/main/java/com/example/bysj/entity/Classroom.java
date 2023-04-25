package com.example.bysj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Classroom {
    private long id;

    private String class_name;

    private String teacher_name;

    private String grade;
}
