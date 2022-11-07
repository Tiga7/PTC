package com.ptc.backend.service.impl;

import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.impl.userutils.UserDetailsImpl;
import com.ptc.backend.service.user.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {

    @Override
    public OrdinaryUser getInfo() {
        //授权成功会从上下文找中获取用户的信息  (传回的token信息)
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //会进数据库查询
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        OrdinaryUser user = loginUser.getUser();

        user.setPassword("");
        return user;
    }
}
