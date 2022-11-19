package com.ptc.backend.service.impl.collection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptc.backend.pojo.Collection;
import com.ptc.backend.pojo.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Gan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionVo {
    private Integer id;
    private Integer userId;
    private Integer roomId;
    private String buildingName;
    private String roomName;
    private Integer capacity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    public void putValue(Collection collection, Room room) {
        id = collection.getId();
        userId = collection.getUserId();
        roomId = collection.getRoomId();
        buildingName = room.getBuildingName();
        roomName = room.getRoomName();
        capacity = room.getCapacity();
        modifyTime = collection.getModifyTime();
    }
}
