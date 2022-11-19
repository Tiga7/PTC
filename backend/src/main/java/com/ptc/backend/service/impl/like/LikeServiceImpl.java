package com.ptc.backend.service.impl.like;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptc.backend.mapper.LikeMapper;
import com.ptc.backend.pojo.Like;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.impl.userutils.UserDetailsImpl;
import com.ptc.backend.service.like.LikeService;
import com.ptc.backend.controller.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {

    @Autowired
    private LikeMapper likeMapper;


    @Override
    public ResultData<String> changeLike(Integer recordId) {
        //授权成功会从上下文找中获取用户的信息  (传回的token信息)
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //会进数据库查询
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        OrdinaryUser user = loginUser.getUser();

        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId()).eq("record_id", recordId);

        Like like = likeMapper.selectOne(queryWrapper);
        queryWrapper.clear();
        //如果不存在记录则新增插入
        if (like == null) {
            like = new Like();
            like.setUserId(user.getId());
            like.setRecordId(recordId);
            likeMapper.insert(like);
        } else {//否则
            like.setStatus(1 ^ like.getStatus());
            UpdateWrapper<Like> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", like.getId());
            likeMapper.update(like, updateWrapper);
        }
        return ResultData.success(null);

    }

    @Override
    public Long likeCount(Integer recordId) {
        QueryWrapper<Like> qw = new QueryWrapper<>();
        qw.eq("id",recordId);
        return likeMapper.selectCount(qw);
    }

}
