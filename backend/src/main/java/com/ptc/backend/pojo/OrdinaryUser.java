package com.ptc.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ordinary_user")
@Builder
public class OrdinaryUser {

    @TableId(type = IdType.AUTO)//主键自增
    private Integer id;
    private String sno;
    private String username;
    private String password;
    private String phone;
    private String gender;
    private String photo;
    private LocalDateTime modifyTime;
    private Integer isAdmin;
    private Integer state;
}
