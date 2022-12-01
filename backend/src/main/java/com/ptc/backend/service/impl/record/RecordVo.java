package com.ptc.backend.service.impl.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.pojo.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordVo {
    private Integer id;
    private Integer userId;
    private Integer roomId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;
    private String description;
    private String username;
    private String photo;
    private String buildingName;
    private String roomName;

    //Todo:点赞数 评论数 是否点赞

    private Long likeCount;
    private Long commentCount;
    private Boolean isLiked;

}
