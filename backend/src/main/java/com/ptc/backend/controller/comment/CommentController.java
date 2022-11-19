package com.ptc.backend.controller.comment;

import com.alibaba.fastjson.JSONObject;
import com.ptc.backend.controller.utils.ResultData;
import com.ptc.backend.controller.utils.ReturnCode;
import com.ptc.backend.pojo.Comment;
import com.ptc.backend.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/list/")
    public JSONObject getList(@RequestParam Map<String, String> data) {

        int page = Integer.parseInt(data.get("page"));
        int size = Integer.parseInt(data.get("size"));
        int recordId = Integer.parseInt(data.get("record_id"));

        return commentService.getList(page, size, recordId);
    }

    @PostMapping()
    public ResultData<String> addComment(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));
        Integer recordId = Integer.parseInt(data.get("record_id"));
        String comment = data.get("comment");
        if (comment == null || comment.length() == 0) {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "评论不能为空");
        }
        return commentService.addComment(Comment.builder()
                .userId(userId)
                .recordId(recordId)
                .comment(comment)
                .modifyTime(LocalDateTime.now())
                .build());
    }

    @DeleteMapping()
    public String removeComment(@RequestParam Map<String, String> data) {
        Integer commentId = Integer.parseInt(data.get("id"));
        return commentService.removeComment(commentId);
    }

}
