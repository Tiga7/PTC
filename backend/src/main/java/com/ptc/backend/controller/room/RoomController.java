package com.ptc.backend.controller.room;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ptc.backend.pojo.Room;
import com.ptc.backend.service.room.RoomService;
import com.ptc.backend.controller.utils.ResultData;
import com.ptc.backend.controller.utils.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/room/")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("list")
    public ResultData<IPage<Room>> getList(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        Integer size = Integer.parseInt(data.get("size"));
        if (page == 0 || size == 0) {
            return ResultData.fail(ReturnCode.FAIL.getCode(), "分页参数出错");
        }
        String roomName = data.get("roomName");
        String buildingName = data.get("buildingName");
        if (roomName != null || buildingName != null )
        {
            return ResultData.success(roomService.getSearchList(page,size,roomName,buildingName));
        }else {
            return ResultData.success(roomService.getList(page, size));
        }
    }

    @PostMapping("schedule")
    public JSONObject getSchedule(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        Integer size = Integer.parseInt(data.get("size"));
        if (page == 0 || size == 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message","分页出错");
            return jsonObject;
        }
        String roomName = data.get("roomName");
        String buildingName = data.get("buildingName");
        return roomService.getRoomSchedule(page,size,roomName,buildingName);
    }

    @PostMapping
    public String addRoom(@RequestParam Map<String, String> data) {
        String buildingName = data.get("buildingName");
        String roomName = data.get("roomName");

        String capacity = data.get("capacity");

        if (buildingName == null || buildingName.length() == 0
                || roomName == null || roomName.length() == 0
                || capacity == null || capacity.length() == 0
        ) {
            return "参数不能为空";
        }
        return roomService.addRoom(Room.builder()
                .buildingName(buildingName)
                .roomName(roomName)
                .capacity(Integer.parseInt(capacity)).build());
    }

    @DeleteMapping
    public String delete(@RequestParam Map<String, String> data) {
        Integer roomId = Integer.parseInt(data.get("id"));
        return roomService.deleteRoom(roomId);
    }

    @PutMapping
    public String editRoom(@RequestParam Map<String, String> data) {

        Integer roomId = Integer.parseInt(data.get("id"));
        String buildingName = data.get("buildingName");
        String roomName = data.get("roomName");
        Integer capacity = Integer.parseInt(data.get("capacity"));

        return roomService.updateRoom(Room.builder()
                .id(roomId)
                .buildingName(buildingName)
                .roomName(roomName)
                .capacity(capacity)
                .build());
    }
}
