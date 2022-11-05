package com.ptc.backend.service.impl;

import com.ptc.backend.config.filters.JwtUtil;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.impl.userutils.UserDetailsImpl;
import com.ptc.backend.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String sno, String password) {

        //将username password 转成密文
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(sno, password);

        //从数据库中获取username对应的用户并进行比对
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();

        OrdinaryUser user = loginUser.getUser();
        Map<String, String> map = new HashMap<>();

        if  (user.getModifyTime().isBefore(LocalDateTime.of(2022,11,1,0,0,0)))
        {
            map.put("result", "failed");
            map.put("error_massage", "account unactivated !");
            return map;
        }
        //封装userid的信息到token里
        String jwt = JwtUtil.createJWT(user.getId().toString());

        map.put("result", "success");
        map.put("error_massage", "get token success");
        map.put("token", jwt);
        return map;

    }
}
