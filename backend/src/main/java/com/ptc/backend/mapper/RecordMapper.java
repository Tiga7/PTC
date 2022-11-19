package com.ptc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptc.backend.pojo.Record;
import com.ptc.backend.service.impl.record.RecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    List<RecordVo> selectRecordVO();
}
