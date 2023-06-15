package com.makeid.makeflow.workflow.service;

import com.makeid.makeflow.template.flow.model.activity.Participant;
import com.makeid.makeflow.template.flow.model.settings.ApprovalSettings;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.task.TaskInst;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Service
public class TaskService {

    public List<TaskInst> createTask(List<Participant> participants, ApprovalSettings approvalSettings, PvmActivity pvmActivity) {

        return Collections.emptyList();
    }
}
