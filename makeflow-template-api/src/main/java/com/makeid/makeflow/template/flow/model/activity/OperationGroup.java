package com.makeid.makeflow.template.flow.model.activity;

import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;
import com.makeid.makeflow.template.flow.model.base.FlowElement;

import java.util.List;

/**
 * 操作组
 * 
 * @author Administrator
 *
 */
public class OperationGroup extends FlowElement {

	private static final long serialVersionUID = 1L;

	private String parentCodeId;// 操作组在哪个节点中，，这个是节点的id

	private String checkType;// 或签 会签 依次

	private List<Participant> participants;// 参与者
	
	private List<CarbonCopy> carbonCopys; //抄送


	public OperationGroup(){
		setElementType(ElementTypeEnum.ACTIVITYTYPE_OPERATIONGROUP.getContext());
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public String getParentCodeId() {
		return parentCodeId;
	}

	public void setParentCodeId(String parentCodeId) {
		this.parentCodeId = parentCodeId;
	}

	public List<CarbonCopy> getCarbonCopys() {
		return carbonCopys;
	}

	public void setCarbonCopys(List<CarbonCopy> carbonCopys) {
		this.carbonCopys = carbonCopys;
	}


	@Override
	public OperationGroup clone() {
		return null;
	}

	@Override
	public void setValues(FlowElement otherElement) {
		super.setValues(otherElement);
		setName(otherElement.getName());
	}
}
