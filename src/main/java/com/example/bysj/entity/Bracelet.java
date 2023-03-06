package com.example.bysj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Bracelet {
    private long id;

    private String blood_oxygen;

    private Integer  max_heart_rate;

    private Integer min_heart_rate;

    private Integer avg_heart_rate;

    private Integer now_heart_rate;

    private String state;
}
