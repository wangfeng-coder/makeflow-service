package com.makeid.makeflow.template.flow.rule;

/**
 * 逻辑规则
 * 
 * @author Administrator
 *
 */
public class RuleLogic extends BaseRule {

	private static final long serialVersionUID = 1L;

	LogicTypeEnum logicTypeEnum;

	public LogicTypeEnum getLogicTypeEnum() {
		return logicTypeEnum;
	}

	public void setLogicTypeEnum(LogicTypeEnum logicTypeEnum) {
		this.logicTypeEnum = logicTypeEnum;
	}
}
