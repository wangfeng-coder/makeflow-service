package com.makeid.makeflow.template.flow.model.activity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *@program
 *@description 抄送人
 *@author feng_wf
 *@create 2023/5/23
 */
public class CarbonCopy implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3995351787295043232L;
	
	private String type; //抄送类型

	
	private List<String> fids;//抄送人id

	private Map<String, Object> descInfo; //抄送人扩展字段


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
