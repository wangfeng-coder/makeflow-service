package com.makeid.makeflow.template.flow.rule;

import java.io.Serializable;
import java.util.List;

/**
 * 规则的基础类
 * @author Administrator
 *
 */
public class BaseRule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<BaseRule> ruleList;

	public List<BaseRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<BaseRule> ruleList) {
		this.ruleList = ruleList;
	}




	
	
	
}
