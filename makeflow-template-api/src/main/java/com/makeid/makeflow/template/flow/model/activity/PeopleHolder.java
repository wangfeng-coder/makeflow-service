package com.makeid.makeflow.template.flow.model.activity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 参与者
 * 
 * @author Administrator
 *
 */
public class PeopleHolder implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;// 参与者的类型 有可能是角色，指定人员，部门，表单内人员字段，表单内部门字段，发起人上级，发起人

	private List<String> fids;// 参与者的id

	private Map<String, Object> descInfo;//其他描述信息，如上级循环的截止部门 update by lusheng 新增原因(上级循环)
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getFids() {
		return fids;
	}

	public void setFids(List<String> fids) {
		this.fids = fids;
	}

	public Map<String, Object> getDescInfo() {
		return descInfo;
	}

	public void setDescInfo(Map<String, Object> descInfo) {
		this.descInfo = descInfo;
	}

}
