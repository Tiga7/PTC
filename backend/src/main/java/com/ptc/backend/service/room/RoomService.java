package com.ptc.backend.service.room;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ptc.backend.pojo.Room;
import com.ptc.backend.service.impl.room.RoomSchedule;

import java.util.List;

public interface RoomService {
    IPage<Room> getList(Integer page, Integer size);

    IPage<Room> getSearchList(Integer page, Integer size, String roomName, String buildingName);

    JSONObject getRoomSchedule(Integer page, Integer size, String roomName, String buildingName);

    String addRoom(Room room);

    String updateRoom(Room room);

    String deleteRoom(Integer roomId);
}
