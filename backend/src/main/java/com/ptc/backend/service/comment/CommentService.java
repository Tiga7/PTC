package com.ptc.backend.service.comment;

import com.alibaba.fastjson.JSONObject;
import com.ptc.backend.controller.utils.ResultData;
import com.ptc.backend.pojo.Comment;

public interface CommentService {
    JSONObject getList(Integer page, Integer size, Integer recordId);

    String removeComment(Integer commentId);

    public ResultData<String> addComment(Comment comment);
}
