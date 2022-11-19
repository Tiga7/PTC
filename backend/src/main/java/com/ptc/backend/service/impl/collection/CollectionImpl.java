package com.ptc.backend.service.impl.collection;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ptc.backend.mapper.CollectionMapper;
import com.ptc.backend.mapper.RoomMapper;
import com.ptc.backend.pojo.Collection;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.pojo.Room;
import com.ptc.backend.service.Po2Vo;
import com.ptc.backend.service.collection.CollectionService;
import com.ptc.backend.service.impl.userutils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public JSONObject getList(Integer page, Integer size) {
        //授权成功会从上下文找中获取用户的信息  (传回的token信息)
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //会进数据库查询
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        OrdinaryUser user = loginUser.getUser();

        IPage<Collection> iPage = new Page<>(page, size);
        QueryWrapper<Collection> qw = new QueryWrapper<>();
        qw.eq("user_id", user.getId());
        IPage<Collection> iPage1 = collectionMapper.selectPage(iPage, qw);
        List<Collection> records = iPage1.getRecords();

        //collection To collectionVo
        List<CollectionVo> collectionVos = new ArrayList<>();

        QueryWrapper<Room> qwForRoom = new QueryWrapper<>();
        CollectionVo collectionVo = new CollectionVo();
        for (Collection collection : records) {
            qwForRoom.clear();
            Integer roomId = collection.getRoomId();
            qwForRoom.eq("id", roomId);
            Room room = roomMapper.selectOne(qwForRoom);
            if (room == null) continue;
            CollectionVo collectionVo1 = Po2Vo.INSTANCE.collectionAndRoom2Vo(collection, room);
//            collectionVo.putValue(collection, room);
//            collectionVos.add(collectionVo);
            collectionVos.add(Po2Vo.INSTANCE.collectionAndRoom2Vo(collection, room));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("collectionVo", collectionVos);
        jsonObject.put("total_count", iPage1.getTotal());
        return jsonObject;

    }

    @Override
    public String addCollection(Integer roomId) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //会进数据库查询
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        OrdinaryUser user = loginUser.getUser();

        collectionMapper.insert(Collection.builder()
                .roomId(roomId)
                .userId(user.getId())
                .modifyTime(LocalDateTime.now())
                .build());
        return "success";
    }

    @Override
    public String removeCollection(Integer collectionId) {
        collectionMapper.deleteById(collectionId);
        return "success";
    }
}
