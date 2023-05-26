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
import com.makeid.makeflow.template.bpmn.model.BpmnModel;
import com.makeid.makeflow.template.bpmn.model.ExtensionAttribute;
import com.makeid.makeflow.template.bpmn.util.BpmnXMLUtil;
import org.apache.commons.lang3.StringUtils;
import com.makeid.makeflow.template.bpmn.model.Process;
import javax.xml.stream.XMLStreamReader;
import java.util.List;

import static java.util.Arrays.asList;

/**

 */
public class ProcessParser implements BpmnXMLConstants {

  public static final List<ExtensionAttribute> defaultProcessAttributes = asList(new ExtensionAttribute(ATTRIBUTE_ID), new ExtensionAttribute(ATTRIBUTE_NAME), new ExtensionAttribute(
          ATTRIBUTE_PROCESS_EXECUTABLE), new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_PROCESS_CANDIDATE_USERS), new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE,
          ATTRIBUTE_PROCESS_CANDIDATE_GROUPS));

  public Process parse(XMLStreamReader xtr, BpmnModel model) throws Exception {
    Process process = null;
    if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_ID))) {
      String processId = xtr.getAttributeValue(null, ATTRIBUTE_ID);
      process = new Process();
      process.setId(processId);
      BpmnXMLUtil.addXMLLocation(process, xtr);
      process.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
      if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_PROCESS_EXECUTABLE))) {
        process.setExecutable(Boolean.parseBoolean(xtr.getAttributeValue(null, ATTRIBUTE_PROCESS_EXECUTABLE)));
      }
      String candidateUsersString = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_PROCESS_CANDIDATE_USERS);
      if (StringUtils.isNotEmpty(candidateUsersString)) {
        List<String> candidateUsers = BpmnXMLUtil.parseDelimitedList(candidateUsersString);
        process.setCandidateStarterUsers(candidateUsers);
      }
      String candidateGroupsString = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_PROCESS_CANDIDATE_GROUPS);
      if (StringUtils.isNotEmpty(candidateGroupsString)) {
        List<String> candidateGroups = BpmnXMLUtil.parseDelimitedList(candidateGroupsString);
        process.setCandidateStarterGroups(candidateGroups);
      }

      BpmnXMLUtil.addCustomAttributes(xtr, process, defaultProcessAttributes);

      model.getProcesses().add(process);

    }
    return process;
  }
}
