package com.ptc.backend.service;

import com.ptc.backend.pojo.*;
import com.ptc.backend.service.impl.collection.CollectionVo;
import com.ptc.backend.service.impl.comment.CommentVo;
import com.ptc.backend.service.impl.record.RecordVo;
import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper
public interface Po2Vo {
    Po2Vo INSTANCE = Mappers.getMapper(Po2Vo.class);

    /**
     * 一个对象转为一个对象
     *
     * @param collection
     * @return
     */
    CollectionVo toVo(Collection collection);

    /**
     * @param collection
     * @param room       多个对象转为一个对象
     * @return
     */
    @Mapping(source = "collection.id", target = "id")
    @Mapping(source = "collection.userId", target = "userId")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.buildingName", target = "buildingName")
    @Mapping(source = "room.roomName", target = "roomName")
    @Mapping(source = "room.capacity", target = "capacity")
    @Mapping(source = "collection.modifyTime", target = "modifyTime")
    CollectionVo collectionAndRoom2Vo(Collection collection, Room room);

    /**
     *
     * @param record
     * @param user
     * @return
     */
    @Mapping(target = "id", source = "record.id")
    @Mapping(target = "userId", source = "record.userId")
    @Mapping(target = "roomId", source = "record.roomId")
    @Mapping(target = "modifyTime", source = "record.modifyTime")
    @Mapping(target = "description", source = "record.description")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "photo", source = "user.photo")
    @Mapping(target = "buildingName", source = "room.buildingName")
    @Mapping(target = "roomName", source = "room.roomName")
    RecordVo recordAndUser2Vo(Record record, OrdinaryUser user,Room room);

    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "userId", source = "comment.userId")
    @Mapping(target = "recordId", source = "comment.recordId")
    @Mapping(target = "comment", source = "comment.comment")
    @Mapping(target = "modifyTime", source = "comment.modifyTime")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "photo", source = "user.photo")
    CommentVo commentAndUser2Vo(Comment comment , OrdinaryUser user);

}
