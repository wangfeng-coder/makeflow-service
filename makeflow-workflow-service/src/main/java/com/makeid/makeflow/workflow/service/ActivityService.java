package com.makeid.makeflow.workflow.service;

import com.makeid.makeflow.workflow.dao.ActivityDao;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-08
 */
@Service
public class ActivityService {

    @Resource
    private ActivityDao activityDao;

    public ActivityImpl findById(String id) {
        ActivityEntity activityEntity = activityDao.findById(id);
        //转换成具体类型的活动节点实列
        return null;
    }
}
