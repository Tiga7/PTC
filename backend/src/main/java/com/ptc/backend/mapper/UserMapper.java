package com.ptc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptc.backend.pojo.OrdinaryUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<OrdinaryUser> {
}
