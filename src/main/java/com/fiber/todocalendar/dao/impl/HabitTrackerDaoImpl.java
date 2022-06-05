package com.fiber.todocalendar.dao.impl;

import com.fiber.todocalendar.dao.HabitTrackerDao;
import com.fiber.todocalendar.dto.HabitTrackerQueryParams;
import com.fiber.todocalendar.dto.PatchRequest;
import com.fiber.todocalendar.model.HabitTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


@Component
public class HabitTrackerDaoImpl implements HabitTrackerDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 條件查询原子習慣追蹤
     *
     * @param habitTrackerQueryParams
     * @return
     */
    @Override
    public HabitTracker getHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams) {
        Query query = new Query(Criteria.where("habitId").is(habitTrackerQueryParams.getHabitId())
                .and("year").is(habitTrackerQueryParams.getYear()));
        HabitTracker habitTracker = mongoTemplate.findOne(query, HabitTracker.class);
        return habitTracker;
    }

    /**
     * 增加原子習慣追蹤的圈選日期
     *
     * @param patchRequest
     * @return
     */
    @Override
    public void addPickedDay(HabitTrackerQueryParams habitTrackerQueryParams, PatchRequest patchRequest) {
        Query query = new Query(Criteria.where("habitId").is(habitTrackerQueryParams.getHabitId())
                .and("year").is(habitTrackerQueryParams.getYear()));
        boolean isHabitTrackerExists = mongoTemplate.exists(query, "habitTrackers");
        if (!isHabitTrackerExists) {
            createHabitTracker(habitTrackerQueryParams);
        }
        Update update = new Update().push("pickedDays", patchRequest.getValue()).set("lastModifiedTime", System.currentTimeMillis());
        mongoTemplate.updateFirst(query, update, "habitTrackers");
    }

    /**
     * 移除原子習慣追蹤的圈選日期
     *
     * @param patchRequest
     * @return
     */
    @Override
    public void removePickedDay(HabitTrackerQueryParams habitTrackerQueryParams, PatchRequest patchRequest) {
        Query query = new Query(Criteria.where("habitId").is(habitTrackerQueryParams.getHabitId())
                .and("year").is(habitTrackerQueryParams.getYear()));
        Update update = new Update().pull("pickedDays", patchRequest.getValue()).set("lastModifiedTime", System.currentTimeMillis());
        mongoTemplate.updateFirst(query, update, "habitTrackers");
    }

    /**
     * 建立原子習慣追蹤
     *
     * @param habitTrackerQueryParams
     * @return
     */
    private void createHabitTracker(HabitTrackerQueryParams habitTrackerQueryParams) {
        HabitTracker habitTracker = new HabitTracker(habitTrackerQueryParams.getHabitId(), habitTrackerQueryParams.getYear());
        mongoTemplate.insert(habitTracker, "habitTrackers");
    }
}
