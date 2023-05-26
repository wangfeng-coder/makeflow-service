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
package com.makeid.makeflow.template.bpmn.conventer.parser;


import com.makeid.makeflow.template.bpmn.constants.BpmnXMLConstants;
import com.makeid.makeflow.template.bpmn.model.*;
import com.makeid.makeflow.template.bpmn.model.Process;
import com.makeid.makeflow.template.bpmn.util.BpmnXMLUtil;

import javax.xml.stream.XMLStreamReader;
import java.util.List;

/**

 */
public class ExtensionElementsParser implements BpmnXMLConstants {

  public void parse(XMLStreamReader xtr, List<SubProcess> activeSubProcessList, Process activeProcess, BpmnModel model) throws Exception {
    BaseElement parentElement = null;
    if (!activeSubProcessList.isEmpty()) {
      parentElement = activeSubProcessList.get(activeSubProcessList.size() - 1);

    } else {
      parentElement = activeProcess;
    }

    boolean readyWithChildElements = false;
    while (readyWithChildElements == false && xtr.hasNext()) {
      xtr.next();
      if (xtr.isStartElement()) {
        if (ELEMENT_EXECUTION_LISTENER.equals(xtr.getLocalName())) {
          new ExecutionListenerParser().parseChildElement(xtr, parentElement, model);
        } else if (ELEMENT_EVENT_LISTENER.equals(xtr.getLocalName())) {
          new ActivitiEventListenerParser().parseChildElement(xtr, parentElement, model);
        }  else {
          ExtensionElement extensionElement = BpmnXMLUtil.parseExtensionElement(xtr);
          parentElement.addExtensionElement(extensionElement);
        }

      } else if (xtr.isEndElement()) {
        if (ELEMENT_EXTENSIONS.equals(xtr.getLocalName())) {
          readyWithChildElements = true;
        }
      }
    }
  }
}
