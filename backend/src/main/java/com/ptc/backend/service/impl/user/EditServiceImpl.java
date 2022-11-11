package com.ptc.backend.service.impl.user;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ptc.backend.mapper.UserMapper;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.impl.userutils.GetLoginUser;
import com.ptc.backend.service.impl.userutils.UserDetailsImpl;
import com.ptc.backend.service.user.EditService;
import com.ptc.backend.utils.ResultData;
import com.ptc.backend.utils.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EditServiceImpl implements EditService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder Encoder;

    @Override
    public ResultData<String> editUser(String username,String gender, String password, String newPassword) {
        //授权成功会从上下文找中获取用户的信息  (传回的token信息)
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //会进数据库查询
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        OrdinaryUser user = loginUser.getUser();


        if (Encoder.matches(password,user.getPassword()))
        {
            user.setUsername(username);
            user.setGender(gender);
            user.setPassword(Encoder.encode(newPassword));
            UpdateWrapper<OrdinaryUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("sno", user.getSno());
            userMapper.update(user, updateWrapper);
            return ResultData.success("修改信息成功");
        }else {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "密码不正确");
        }
    }
}
