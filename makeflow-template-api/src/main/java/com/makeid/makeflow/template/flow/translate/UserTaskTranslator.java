package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.bpmn.model.UserTask;
import com.makeid.makeflow.template.flow.constants.CheckTypeEnum;
import com.makeid.makeflow.template.flow.constants.ParticipantTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.ApprovalTaskActivity;
import com.makeid.makeflow.template.flow.model.activity.OperationGroup;
import com.makeid.makeflow.template.flow.model.activity.Participant;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class UserTaskTranslator implements Translator<UserTask, ApprovalTaskActivity>{
    @Override
    public ApprovalTaskActivity translate(UserTask userTask) {
        ApprovalTaskActivity approvalTaskActivity = new ApprovalTaskActivity();
        FlowNodePropertyHandler.fillFlowNodeProperty(userTask,approvalTaskActivity);
        fillParticipant(userTask,approvalTaskActivity);
        return approvalTaskActivity;
    }

    public void fillParticipant(UserTask userTask,ApprovalTaskActivity approvalTaskActivity) {
        List<String> candidateUsers = userTask.getCandidateUsers();
        List<String> candidateGroups = userTask.getCandidateGroups();
        //固定人
        List<Participant> participants = new ArrayList<>();
        if(!CollectionUtils.isEmpty(candidateUsers)) {
            Participant participant = new Participant();
            participant.setFids(candidateUsers);
            participant.setType(ParticipantTypeEnum.PERSON.type);
            participants.add(participant);
        }
        //固定角色 TODO
        if(!CollectionUtils.isEmpty(candidateGroups)) {

        }
        OperationGroup operationGroup = new OperationGroup();
        operationGroup.setParticipants(participants);
        operationGroup.setParentCodeId(userTask.getId());
        //TODO 类型先写死
        operationGroup.setCheckType(CheckTypeEnum.AND.type);
        approvalTaskActivity.setOperationGroup(operationGroup);
    }
}
