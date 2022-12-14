package com.ptc.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("table_room")
@Builder
public class Room {
    @TableId(type = IdType.AUTO)//主键自增
    private Integer id;
    private String buildingName;
    private String roomName;
    private Integer capacity;
}
