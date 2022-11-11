package com.ptc.backend.service.impl.userutils;

import com.ptc.backend.pojo.OrdinaryUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


public class GetLoginUser {

    public  static OrdinaryUser getUser(){
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //会进数据库查询
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        OrdinaryUser user = loginUser.getUser();
        return user;
    }
}
