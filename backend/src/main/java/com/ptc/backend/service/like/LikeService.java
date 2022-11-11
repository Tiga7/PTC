package com.ptc.backend.service.like;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ptc.backend.pojo.Like;
import com.ptc.backend.utils.ResultData;

public interface LikeService extends IService<Like> {
    ResultData<String> changeLike (Integer recordId);

}
