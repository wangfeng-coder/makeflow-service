package com.makeid.makeflow.template.flow.rule;


import java.util.List;

/**
 *@program
 *@description 表达式规则
 *@author feng_wf
 *@create 2023/5/23
 */
public class RuleExpress extends BaseRule {

	private static final long serialVersionUID = 1L;

	private String key;

	private RuleOperatorTypeEnum ruleOperatorType;

	/**
	 * value的规则类型
	 * */
	private RuleValueTypeEnum valueType;


	private Object value; // 值


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public RuleOperatorTypeEnum getRuleOperatorType() {
		return ruleOperatorType;
	}

	public void setRuleOperatorType(RuleOperatorTypeEnum ruleOperatorType) {
		this.ruleOperatorType = ruleOperatorType;
	}

	public RuleValueTypeEnum getValueType() {
		return valueType;
	}

	public void setValueType(RuleValueTypeEnum valueType) {
		this.valueType = valueType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
