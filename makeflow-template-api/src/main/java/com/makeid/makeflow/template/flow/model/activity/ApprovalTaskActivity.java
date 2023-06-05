package com.makeid.makeflow.template.flow.model.activity;


import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;
import com.makeid.makeflow.template.flow.model.settings.ApprovalSettings;

/**
 * 多重审批节点
 * 
 * @author Administrator
 *
 */
public class ApprovalTaskActivity extends BaseActivity {

	
    protected OperationGroup operationGroup;

	private ApprovalSettings approvalSettings;//审批设置

	public ApprovalTaskActivity(){
		setElementType(ElementTypeEnum.ACTIVITYTYPE_MULTIAPPROVAL.getContext());;
	}

	public OperationGroup getOperationGroup() {
		return operationGroup;
	}

	public void setOperationGroup(OperationGroup operationGroup) {
		this.operationGroup = operationGroup;
	}

	public ApprovalSettings getApprovalSettings() {
		return approvalSettings;
	}

	public void setApprovalSettings(ApprovalSettings approvalSettings) {
		this.approvalSettings = approvalSettings;
	}

	@Override
	public ApprovalTaskActivity clone() {
		ApprovalTaskActivity approvalTaskActivity = new ApprovalTaskActivity();
		approvalTaskActivity.setValues(this);
		return approvalTaskActivity;
	}


	public void setValues(ApprovalTaskActivity multiApprovalActivity) {
		super.setValues(multiApprovalActivity);
		this.setApprovalSettings(multiApprovalActivity.getApprovalSettings());
	}
}
