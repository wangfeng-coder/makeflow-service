
package com.makeid.makeflow.template.flow.base;

public  class Element {
  /**
   * 数据库id
   */
  protected String id;

  /**
   * 设计式定义的id 单个流程类唯一
   */
  protected String codeId;



  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCodeId() {
    return codeId;
  }

  public void setCodeId(String codeId) {
    this.codeId = codeId;
  }

  public void setValues(Element otherElement) {
    setId(otherElement.getId());
    setCodeId(otherElement.getCodeId());
  }

  @Override
  public  Element clone(){
    Element element = new Element();
    element.setValues(this);
    return element;
  }
}
