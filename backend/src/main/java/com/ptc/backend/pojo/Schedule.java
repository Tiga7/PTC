package com.ptc.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("table_schedule")
public class Schedule {
    private Integer id;
    private Integer roomId;
    private Integer classTime;
    private LocalDate date;
    private String lessonName;
}
