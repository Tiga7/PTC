package com.ptc.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("table_like")
public class Like {

    @TableId(type = IdType.AUTO)//主键自增
    private Integer id;
    private Integer userId;
    private Integer recordId;
    private Integer status;
}
