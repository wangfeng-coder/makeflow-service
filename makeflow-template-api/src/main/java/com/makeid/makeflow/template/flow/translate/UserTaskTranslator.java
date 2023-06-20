package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.bpmn.model.UserTask;
import com.makeid.makeflow.template.flow.constants.CheckTypeEnum;
import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.ApprovalTaskActivity;
import com.makeid.makeflow.template.flow.model.activity.OperationGroup;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.template.util.ElUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class UserTaskTranslator implements Translator<UserTask, ApprovalTaskActivity> {


    @Override
    public ApprovalTaskActivity translate(UserTask userTask) {
        ApprovalTaskActivity approvalTaskActivity = new ApprovalTaskActivity();
        FlowNodePropertyHandler.fillFlowNodeProperty(userTask, approvalTaskActivity);
        fillParticipant(userTask, approvalTaskActivity);
        return approvalTaskActivity;
    }

    public void fillParticipant(UserTask userTask, ApprovalTaskActivity approvalTaskActivity) {
        List<String> candidateUsers = userTask.getCandidateUsers();
        List<String> candidateGroups = userTask.getCandidateGroups();
        String assignee = userTask.getAssignee();
        List<PeopleHolder> participants = new ArrayList<>();
        //下面的解析是 按照现有的
        //单个人
        if (StringUtils.hasText(assignee)) {
            participants.addAll(PeopleResolvers.resolve(assignee));
        }
        //固定人
        if (!CollectionUtils.isEmpty(candidateUsers)) {
            participants.addAll(PeopleResolvers.resolve(assignee));
        }
        //固定角色 TODO
        if (!CollectionUtils.isEmpty(candidateGroups)) {
            participants.addAll(PeopleResolvers.resolve(assignee));
        }
        OperationGroup operationGroup = new OperationGroup();
        operationGroup.setParticipants(participants);
        operationGroup.setParentCodeId(userTask.getId());
        //TODO 类型先写死
        operationGroup.setCheckType(CheckTypeEnum.AND.type);
        approvalTaskActivity.setOperationGroup(operationGroup);
    }
}
