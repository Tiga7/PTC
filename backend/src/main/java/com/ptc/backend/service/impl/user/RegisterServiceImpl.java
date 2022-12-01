package com.ptc.backend.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ptc.backend.mapper.UserMapper;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.service.user.RegisterService;
import com.ptc.backend.controller.utils.ResultData;
import com.ptc.backend.controller.utils.ReturnCode;
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
    public ResultData<String> register(String sno,
                                       String password,
                                       String confirmPwd,
                                       String phone) {
        Map<String, String> data = new HashMap<>();

        QueryWrapper<OrdinaryUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno", sno);
        OrdinaryUser selectOne = userMapper.selectOne(queryWrapper);

        if (selectOne == null) {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "该用户不存在,请联系管理员");
        }

        if (!Encoder.matches(password, selectOne.getPassword())) {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "密码不正确");
        }
        queryWrapper.clear();

        OrdinaryUser user = userMapper.selectOne(queryWrapper.eq("phone", phone));
        if (user != null && !user.getSno().equals(sno)) {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "该手机号已经被其他用户激活过");
        }

//        queryWrapper.clear();
//        OrdinaryUser user1 = userMapper.selectOne(queryWrapper.eq("sno", sno));
//        if (user1!=null && !user1.getPhone().equals(phone))
//        {
//            return ResultData.fail(ReturnCode.FAIL.getCode(), "更改手机号请联系管理员");
//        }

        selectOne.setModifyTime(LocalDateTime.now());
        selectOne.setUsername(sno);
        selectOne.setPhone(phone);
        selectOne.setPhoto("https://cdn.acwing.com/media/user/profile/photo/41542_lg_e423f93596.jpg");
        UpdateWrapper<OrdinaryUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("sno", sno);
        userMapper.update(selectOne, updateWrapper);

        return ResultData.success(null);

    }
}
