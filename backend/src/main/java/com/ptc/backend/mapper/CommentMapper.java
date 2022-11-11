package com.ptc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptc.backend.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Gan
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
