package com.ptc.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("table_record")
public class Record {
    @TableId(type = IdType.AUTO)//主键自增
    private Integer id;
    private Integer userId;
    private Integer roomId;
    private LocalDateTime modifyTime;
    private String description;

}
