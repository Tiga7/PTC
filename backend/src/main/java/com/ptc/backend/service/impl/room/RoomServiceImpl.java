package com.ptc.backend.service.impl.room;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ptc.backend.mapper.CollectionMapper;
import com.ptc.backend.mapper.RoomMapper;
import com.ptc.backend.mapper.ScheduleMapper;
import com.ptc.backend.pojo.Collection;
import com.ptc.backend.pojo.OrdinaryUser;
import com.ptc.backend.pojo.Room;
import com.ptc.backend.pojo.Schedule;
import com.ptc.backend.service.impl.userutils.UserDetailsImpl;
import com.ptc.backend.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public IPage<Room> getList(Integer page, Integer size) {
        IPage<Room> iPage = new Page<>(page, size);
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("building_name");
        IPage<Room> iPage1 = roomMapper.selectPage(iPage, queryWrapper);
        return roomMapper.selectPage(iPage, null);
    }

    @Override
    public IPage<Room> getSearchList(Integer page, Integer size, String roomName, String buildingName) {
        IPage<Room> iPage = new Page<>(page, size);
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        if (buildingName != null && buildingName.length() != 0) {
            queryWrapper.eq("building_name", buildingName);
            queryWrapper.orderByAsc("building_name");
        }
        if (roomName != null && roomName.length() != 0) {
            queryWrapper.eq("room_name", roomName);
            queryWrapper.orderByAsc("building_name");
        }
        return roomMapper.selectPage(iPage, queryWrapper);
    }

    @Override
    public JSONObject getRoomSchedule(Integer page,
                                      Integer size,
                                      String roomName,
                                      String buildingName) {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //会进数据库查询
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        OrdinaryUser user = loginUser.getUser();

        IPage<Room> iPage = new Page<>(page, size);
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();

        QueryWrapper<Schedule> qw = new QueryWrapper<>();

        QueryWrapper<Collection> qwForCollection = new QueryWrapper<>();

        if (buildingName != null && buildingName.length() != 0) {
            queryWrapper.eq("building_name", buildingName);
            queryWrapper.orderByAsc("building_name");
        }
        if (roomName != null && roomName.length() != 0) {
            queryWrapper.eq("room_name", roomName);
            queryWrapper.orderByAsc("building_name");
        }

        IPage<Room> iPage1 = roomMapper.selectPage(iPage, queryWrapper);
        List<Room> records = iPage1.getRecords();

        List<RoomSchedule> schedules = new ArrayList<>();
        for (Room room : records) {
            Integer id = room.getId();
            qw.clear();
            qw.eq("room_id", id).eq("date", LocalDate.now()).orderByAsc("class_time");
            List<Schedule> schedules1 = scheduleMapper.selectList(qw);
            List<Integer> classTime = new ArrayList<>();
            for (Schedule schedule : schedules1) {
                classTime.add(schedule.getClassTime());
            }

            RoomSchedule roomSchedule = new RoomSchedule();
            roomSchedule.setId(room.getId());
            roomSchedule.setBuildingName(room.getBuildingName());
            roomSchedule.setRoomName(room.getRoomName());
            roomSchedule.setCapacity(room.getCapacity());
            roomSchedule.setClassTime(classTime);

            qwForCollection.clear();
            qwForCollection.eq("user_id", user.getId()).eq("room_id", room.getId());
            Collection collection = collectionMapper.selectOne(qwForCollection);
            if (collection != null) {
                roomSchedule.setCollectionId(collection.getId());
            } else {
                roomSchedule.setCollectionId(-1);
            }
            schedules.add(roomSchedule);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("schedules", schedules);
        jsonObject.put("total_count", iPage1.getTotal());
        return jsonObject;
    }

    @Override
    public String addRoom(Room room) {
        roomMapper.insert(room);
        return "添加成功";
    }

    @Override
    public String updateRoom(Room room) {
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", room.getId());
        int update = roomMapper.update(room, updateWrapper);
        if (update == 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    @Override
    public String deleteRoom(Integer roomId) {
        roomMapper.deleteById(roomId);
        return "删除成功";
    }
}

