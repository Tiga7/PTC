package com.ptc.backend.controller.like;

import com.ptc.backend.service.like.LikeService;
import com.ptc.backend.controller.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/api/like/add/")
    public ResultData<String> addLike(@RequestParam Map<String, String> data, Principal principal) {
        int recordId = Integer.parseInt(data.get("record_id"));

        return likeService.changeLike(recordId);
    }

}
