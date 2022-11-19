package com.ptc.backend.service.impl.comment;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ptc.backend.controller.utils.ResultData;
import com.ptc.backend.mapper.CommentMapper;
import com.ptc.backend.mapper.UserMapper;
import com.ptc.backend.pojo.Comment;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.Po2Vo;
import com.ptc.backend.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getList(Integer page, Integer size, Integer recordId) {
        IPage<Comment> iPage = new Page<>(page, size);
        QueryWrapper<Comment> qw = new QueryWrapper<>();
        qw.eq("record_id", recordId).orderByDesc("modify_time");
        IPage<Comment> commentIPage = commentMapper.selectPage(iPage, qw);
        List<Comment> records = commentIPage.getRecords();

        List<CommentVo> commentVos = new ArrayList<>();
        QueryWrapper<OrdinaryUser> userQueryWrapper = new QueryWrapper<>();
        for (Comment comment : records) {
            userQueryWrapper.clear();
            userQueryWrapper.eq("id", comment.getUserId());
            OrdinaryUser user = userMapper.selectOne(userQueryWrapper);
            commentVos.add(Po2Vo.INSTANCE.commentAndUser2Vo(comment, user));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("comments", commentVos);
        jsonObject.put("total_count", commentIPage.getTotal());
        return jsonObject;
    }

    @Override
    public ResultData<String> addComment(Comment comment) {
        commentMapper.insert(comment);
        return ResultData.success("评论成功");
    }

    @Override
    public String removeComment(Integer commentId) {
        commentMapper.deleteById(commentId);
        return "success";
    }
}
