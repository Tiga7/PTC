package com.ptc.backend.service.impl.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomSchedule {
    private Integer id;
    private String buildingName;
    private String roomName;
    private Integer capacity;
    private List<Integer> classTime;
    private Integer collectionId;
}
