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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class BpmnModel {

  protected Map<String, List<ExtensionAttribute>> definitionsAttributes = new LinkedHashMap<String, List<ExtensionAttribute>>();
  protected List<Process> processes = new ArrayList<Process>();
  protected Map<String, String> namespaceMap = new LinkedHashMap<String, String>();

  protected Map<String, GraphicInfo> locationMap = new LinkedHashMap<String, GraphicInfo>();

  protected Map<String, GraphicInfo> labelLocationMap = new LinkedHashMap<String, GraphicInfo>();

  protected Map<String, List<GraphicInfo>> flowLocationMap = new LinkedHashMap<String, List<GraphicInfo>>();
  protected String targetNamespace;
  protected String sourceSystemId;
  protected List<String> userTaskFormTypes;
  protected List<String> startEventFormTypes;
  protected int nextFlowIdCounter = 1;
  protected Object eventSupport;

  public Map<String, List<ExtensionAttribute>> getDefinitionsAttributes() {
    return definitionsAttributes;
  }

  public String getDefinitionsAttributeValue(String namespace, String name) {
    List<ExtensionAttribute> attributes = getDefinitionsAttributes().get(name);
    if (attributes != null && !attributes.isEmpty()) {
      for (ExtensionAttribute attribute : attributes) {
        if (namespace.equals(attribute.getNamespace()))
          return attribute.getValue();
      }
    }
    return null;
  }

  public void addDefinitionsAttribute(ExtensionAttribute attribute) {
    if (attribute != null && StringUtils.isNotEmpty(attribute.getName())) {
      List<ExtensionAttribute> attributeList = null;
      if ( !this.definitionsAttributes.containsKey(attribute.getName())) {
        attributeList = new ArrayList<ExtensionAttribute>();
        this.definitionsAttributes.put(attribute.getName(), attributeList);
      }
      this.definitionsAttributes.get(attribute.getName()).add(attribute);
    }
  }

  public void setDefinitionsAttributes(Map<String, List<ExtensionAttribute>> attributes) {
    this.definitionsAttributes = attributes;
  }


  public Process getProcessById(String id) {
    for (Process process : processes) {
      if (process.getId().equals(id)) {
        return process;
      }
    }
    return null;
  }

  public Process getOneProcess() {
    for (Process process : processes) {
      if (Objects.nonNull(process)) {
        return process;
      }
    }
    return null;
  }

  public List<Process> getProcesses() {
    return processes;
  }

  public Process getMainProcess() {
    return processes.get(0);
  }

  public void addProcess(Process process) {
    processes.add(process);
  }

  public FlowElement getFlowElement(String id) {
    FlowElement foundFlowElement = null;
    for (Process process : processes) {
      foundFlowElement = process.getFlowElement(id);
      if (foundFlowElement != null) {
        break;
      }
    }

    if (foundFlowElement == null) {
      for (Process process : processes) {
        for (FlowElement flowElement : process.findFlowElementsOfType(SubProcess.class)) {
          foundFlowElement = getFlowElementInSubProcess(id, (SubProcess) flowElement);
          if (foundFlowElement != null) {
            break;
          }
        }
        if (foundFlowElement != null) {
          break;
        }
      }
    }

    return foundFlowElement;
  }

  protected FlowElement getFlowElementInSubProcess(String id, SubProcess subProcess) {
    FlowElement foundFlowElement = subProcess.getFlowElement(id);
    if (foundFlowElement == null) {
      for (FlowElement flowElement : subProcess.getFlowElements()) {
        if (flowElement instanceof SubProcess) {
          foundFlowElement = getFlowElementInSubProcess(id, (SubProcess) flowElement);
          if (foundFlowElement != null) {
            break;
          }
        }
      }
    }
    return foundFlowElement;
  }


  public void addGraphicInfo(String key, GraphicInfo graphicInfo) {
    locationMap.put(key, graphicInfo);
  }

  public GraphicInfo getGraphicInfo(String key) {
    return locationMap.get(key);
  }

  public void removeGraphicInfo(String key) {
    locationMap.remove(key);
  }

  public List<GraphicInfo> getFlowLocationGraphicInfo(String key) {
    return flowLocationMap.get(key);
  }

  public void removeFlowGraphicInfoList(String key) {
    flowLocationMap.remove(key);
  }

  public Map<String, GraphicInfo> getLocationMap() {
    return locationMap;
  }

  public boolean hasDiagramInterchangeInfo() {
    return !locationMap.isEmpty();
  }

  public Map<String, List<GraphicInfo>> getFlowLocationMap() {
    return flowLocationMap;
  }

  public GraphicInfo getLabelGraphicInfo(String key) {
    return labelLocationMap.get(key);
  }

  public void addLabelGraphicInfo(String key, GraphicInfo graphicInfo) {
    labelLocationMap.put(key, graphicInfo);
  }

  public void removeLabelGraphicInfo(String key) {
    labelLocationMap.remove(key);
  }

  public Map<String, GraphicInfo> getLabelLocationMap() {
    return labelLocationMap;
  }

  public void addFlowGraphicInfoList(String key, List<GraphicInfo> graphicInfoList) {
    flowLocationMap.put(key, graphicInfoList);
  }

  public void addNamespace(String prefix, String uri) {
    namespaceMap.put(prefix, uri);
  }

  public boolean containsNamespacePrefix(String prefix) {
    return namespaceMap.containsKey(prefix);
  }

  public String getNamespace(String prefix) {
    return namespaceMap.get(prefix);
  }

  public Map<String, String> getNamespaces() {
    return namespaceMap;
  }

  public String getTargetNamespace() {
    return targetNamespace;
  }

  public void setTargetNamespace(String targetNamespace) {
    this.targetNamespace = targetNamespace;
  }

  public String getSourceSystemId() {
    return sourceSystemId;
  }

  public void setSourceSystemId(String sourceSystemId) {
    this.sourceSystemId = sourceSystemId;
  }

  public List<String> getUserTaskFormTypes() {
    return userTaskFormTypes;
  }

  public void setUserTaskFormTypes(List<String> userTaskFormTypes) {
    this.userTaskFormTypes = userTaskFormTypes;
  }

  public List<String> getStartEventFormTypes() {
    return startEventFormTypes;
  }

  public void setStartEventFormTypes(List<String> startEventFormTypes) {
    this.startEventFormTypes = startEventFormTypes;
  }

  @JsonIgnore
  public Object getEventSupport() {
    return eventSupport;
  }

  public void setEventSupport(Object eventSupport) {
    this.eventSupport = eventSupport;
  }

  public String getStartFormKey(String processId) {
    FlowElement initialFlowElement = getProcessById(processId)
            .getInitialFlowElement();
    if (initialFlowElement instanceof StartEvent) {
      StartEvent startEvent = (StartEvent) initialFlowElement;
      return startEvent.getFormKey();
    }
    return null;
  }
}
