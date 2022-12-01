package com.ptc.backend.service.impl.record;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptc.backend.mapper.*;
import com.ptc.backend.pojo.*;
import com.ptc.backend.service.Po2Vo;
import com.ptc.backend.service.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private LikeMapper likeMapper;

    @Override
    public String addRecord(Record record) {

//        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", record.getUserId());
//        List<Record> records = recordMapper.selectList(queryWrapper);
//
//        for (Record r : records) {
//            if (r.getModifyTime().getDayOfYear() == LocalDate.now().getDayOfYear()
//                    && r.getModifyTime().getYear() == LocalDate.now().getYear()) {
//                return "今天已经打过卡了,请明天再打卡吧.";
//            }
//        }
        if (record.getDescription() == null || record.getDescription().length() == 0) {
            record.setDescription("这个人很懒,打卡什么没说.");
        }
        recordMapper.insert(record);
        return "打卡成功";

    }

    /**
     * 嵌套了太多层查询,有待优化
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public JSONObject getList(Integer page, Integer size, Integer userId) {
        IPage<Record> iPage = new Page<>(page, size);

        IPage<Record> items = recordMapper.selectPage(iPage, new QueryWrapper<Record>().orderByDesc("modify_time"));
        List<Record> records = items.getRecords();

        QueryWrapper<OrdinaryUser> qwForUser = new QueryWrapper<>();
        QueryWrapper<Room> qwForRoom = new QueryWrapper<>();
        QueryWrapper<Like> qwForLike = new QueryWrapper<>();
        QueryWrapper<Comment> qwForComment = new QueryWrapper<>();


        List<RecordVo> recordVos = new ArrayList<>();
        for (Record record : records) {
            qwForUser.clear();
            qwForRoom.clear();
            qwForLike.clear();
            qwForComment.clear();

            qwForUser.eq("id", record.getUserId());
            qwForRoom.eq("id", record.getRoomId());
            qwForComment.eq("record_id", record.getId());
            qwForLike.eq("record_id", record.getId()).eq("status", 1);
            RecordVo recordVo = Po2Vo.INSTANCE.recordAndUser2Vo(record,
                    userMapper.selectOne(qwForUser),
                    roomMapper.selectOne(qwForRoom));

            recordVo.setCommentCount(commentMapper.selectCount(qwForComment));
            recordVo.setLikeCount(likeMapper.selectCount(qwForLike));

            qwForLike.clear();
            qwForLike.eq("user_id", userId).eq("record_id", record.getId());
            Like like = likeMapper.selectOne(qwForLike);
            recordVo.setIsLiked(like != null && like.getStatus() != 0);

            recordVos.add(recordVo);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total_count", items.getTotal());
        jsonObject.put("records", recordVos);

        return jsonObject;
    }
}
