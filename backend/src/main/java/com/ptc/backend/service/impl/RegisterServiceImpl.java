package com.ptc.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ptc.backend.mapper.UserMapper;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder Encoder;

    @Override
    public Map<String, String> register(String sno,
                                        String password,
                                        String confirmPwd,
                                        String phone) {
        Map<String, String> data = new HashMap<>();
        if (sno == null || sno.length() != 9) {
            data.put("error_message", "学号长度不正确");
            return data;
        }

        if (password == null || password.length() == 0) {
            data.put("error_message", "密码不能为空");
            return data;
        }

        if (!password.equals(confirmPwd)) {
            data.put("error_message", "两次密码不一致");
            return data;
        }
        if (phone == null || phone.length() == 0) {
            data.put("error_message", "手机号不能为空");
        }

        QueryWrapper<OrdinaryUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno", sno);
        OrdinaryUser selectOne = userMapper.selectOne(queryWrapper);

        if (selectOne == null) {
            data.put("result", "failed");
            data.put("error_message", "该用户不存在,请联系管理员");
            return data;
        }

        if (!Encoder.matches(password, selectOne.getPassword())) {
            data.put("result", "failed");
            data.put("error_message", "密码不正确");
            return data;
        }
        queryWrapper.clear();

        OrdinaryUser user = userMapper.selectOne(queryWrapper.eq("phone", phone));
        if (user != null && user.getPhone().equals(phone)) {
            data.put("result", "failed");
            data.put("error_message", "该手机号已经被其他用户激活过");
            return data;
        } else {
            selectOne.setModifyTime(LocalDateTime.now());
            selectOne.setUsername(sno);
            selectOne.setPhone(phone);
            selectOne.setPhoto("https://cdn.acwing.com/media/user/profile/photo/41542_lg_e423f93596.jpg");
            UpdateWrapper<OrdinaryUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("sno", sno);
            userMapper.update(selectOne, updateWrapper);
            data.put("result", "success");
            data.put("error_message", "激活成功");
            return data;
        }
    }
}
