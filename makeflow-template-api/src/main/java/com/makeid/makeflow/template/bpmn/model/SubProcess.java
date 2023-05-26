/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.makeid.makeflow.template.bpmn.model;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SubProcess extends Activity implements FlowElementsContainer {

  protected Map<String, FlowElement> flowElementMap = new LinkedHashMap<String, FlowElement>();
  protected List<FlowElement> flowElementList = new ArrayList<FlowElement>();

  public FlowElement getFlowElement(String id) {
    FlowElement foundElement = null;
    if (StringUtils.isNotEmpty(id)) {
      foundElement = flowElementMap.get(id);
    }
    return foundElement;
  }

  public Collection<FlowElement> getFlowElements() {
    return flowElementList;
  }

  public void addFlowElement(FlowElement element) {
    flowElementList.add(element);
    element.setParentContainer(this);
    if(element instanceof FlowElementsContainer){
      flowElementMap.putAll(((FlowElementsContainer) element).getFlowElementMap());
    }
    if (StringUtils.isNotEmpty(element.getId())) {
      flowElementMap.put(element.getId(), element);
      if (getParentContainer() != null) {
        getParentContainer().addFlowElementToMap(element);
      }
    }
  }

  public void addFlowElementToMap(FlowElement element) {
    if (element != null && StringUtils.isNotEmpty(element.getId())) {
      flowElementMap.put(element.getId(), element);
      if (getParentContainer() != null) {
        getParentContainer().addFlowElementToMap(element);
      }
    }
  }
  
  public void removeFlowElement(String elementId) {
    FlowElement element = getFlowElement(elementId);
    if (element != null) {
      flowElementList.remove(element);
      flowElementMap.remove(elementId);
      if (element.getParentContainer() != null) {
        element.getParentContainer().removeFlowElementFromMap(elementId);
      }
    }
  }
  
  public void removeFlowElementFromMap(String elementId) {
    if (StringUtils.isNotEmpty(elementId)) {
      flowElementMap.remove(elementId);
    }
  }
  
  public Map<String, FlowElement> getFlowElementMap() {
    return flowElementMap;
  }

  public void setFlowElementMap(Map<String, FlowElement> flowElementMap) {
    this.flowElementMap = flowElementMap;
  }
  
  public boolean containsFlowElementId(String id) {
    return flowElementMap.containsKey(id);
  }


  public SubProcess clone() {
    SubProcess clone = new SubProcess();
    clone.setValues(this);
    return clone;
  }

  public void setValues(SubProcess otherElement) {
    super.setValues(otherElement);
    flowElementList.clear();
    for (FlowElement flowElement : otherElement.getFlowElements()) {
      addFlowElement(flowElement);
    }
  }
}
