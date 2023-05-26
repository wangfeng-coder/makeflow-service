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
package com.makeid.makeflow.template.bpmn.conventer;

import com.makeid.makeflow.template.bpmn.conventer.parser.BaseChildElementParser;
import com.makeid.makeflow.template.bpmn.model.BaseElement;
import com.makeid.makeflow.template.bpmn.model.BpmnModel;
import com.makeid.makeflow.template.bpmn.model.ExtensionAttribute;
import com.makeid.makeflow.template.bpmn.model.UserTask;
import com.makeid.makeflow.template.bpmn.util.BpmnXMLUtil;
import com.makeid.makeflow.template.bpmn.util.CommaSplitter;
import org.apache.commons.lang3.StringUtils;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.util.*;

import static java.util.Arrays.asList;

/**
 */
public class UserTaskXMLConverter extends BaseBpmnXMLConverter {

  protected Map<String, BaseChildElementParser> childParserMap = new HashMap<String, BaseChildElementParser>();

  /** default attributes taken from bpmn spec and from activiti extension */
  protected static final List<ExtensionAttribute> defaultUserTaskAttributes = asList(
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_FORM_FORMKEY),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_DUEDATE),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_BUSINESS_CALENDAR_NAME),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_ASSIGNEE),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_OWNER),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_PRIORITY),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEUSERS),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEGROUPS),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CATEGORY),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_SERVICE_EXTENSIONID),
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_SKIP_EXPRESSION)
  );

  public UserTaskXMLConverter() {
    HumanPerformerParser humanPerformerParser = new HumanPerformerParser();
    childParserMap.put(humanPerformerParser.getElementName(), humanPerformerParser);
    PotentialOwnerParser potentialOwnerParser = new PotentialOwnerParser();
    childParserMap.put(potentialOwnerParser.getElementName(), potentialOwnerParser);
    CustomIdentityLinkParser customIdentityLinkParser = new CustomIdentityLinkParser();
    childParserMap.put(customIdentityLinkParser.getElementName(), customIdentityLinkParser);
  }

  @Override
public Class<? extends BaseElement> getBpmnElementType() {
    return UserTask.class;
  }

  @Override
  protected String getXMLElementName() {
    return ELEMENT_TASK_USER;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected BaseElement convertXMLToElement(XMLStreamReader xtr, BpmnModel model) throws Exception {
    String formKey = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_FORM_FORMKEY);
    UserTask userTask = new UserTask();
    BpmnXMLUtil.addXMLLocation(userTask, xtr);
    userTask.setDueDate(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_DUEDATE));
    userTask.setBusinessCalendarName(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_BUSINESS_CALENDAR_NAME));
    userTask.setCategory(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CATEGORY));
    userTask.setFormKey(formKey);
    userTask.setAssignee(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_ASSIGNEE));
    userTask.setOwner(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_OWNER));
    userTask.setPriority(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_PRIORITY));

    if (StringUtils.isNotEmpty(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEUSERS))) {
      String expression = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEUSERS);
      userTask.getCandidateUsers().addAll(parseDelimitedList(expression));
    }

    if (StringUtils.isNotEmpty(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEGROUPS))) {
      String expression = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_CANDIDATEGROUPS);
      userTask.getCandidateGroups().addAll(parseDelimitedList(expression));
    }

    userTask.setExtensionId(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_SERVICE_EXTENSIONID));

    if (StringUtils.isNotEmpty(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_SKIP_EXPRESSION))) {
      String expression = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_TASK_USER_SKIP_EXPRESSION);
      userTask.setSkipExpression(expression);
    }

    BpmnXMLUtil.addCustomAttributes(xtr, userTask, defaultElementAttributes,
        defaultActivityAttributes, defaultUserTaskAttributes);

    parseChildElements(getXMLElementName(), userTask, childParserMap, model, xtr);

    return userTask;
  }


  public class HumanPerformerParser extends BaseChildElementParser {

    @Override
    public String getElementName() {
      return "humanPerformer";
    }

    @Override
    public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
      String resourceElement = XMLStreamReaderUtil.moveDown(xtr);
      if (StringUtils.isNotEmpty(resourceElement) && ELEMENT_RESOURCE_ASSIGNMENT.equals(resourceElement)) {
        String expression = XMLStreamReaderUtil.moveDown(xtr);
        if (StringUtils.isNotEmpty(expression) && ELEMENT_FORMAL_EXPRESSION.equals(expression)) {
          ((UserTask) parentElement).setAssignee(xtr.getElementText());
        }
      }
    }
  }

  public class PotentialOwnerParser extends BaseChildElementParser {

    @Override
    public String getElementName() {
      return "potentialOwner";
    }

    @Override
    public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
      String resourceElement = XMLStreamReaderUtil.moveDown(xtr);
      if (StringUtils.isNotEmpty(resourceElement) && ELEMENT_RESOURCE_ASSIGNMENT.equals(resourceElement)) {
        String expression = XMLStreamReaderUtil.moveDown(xtr);
        if (StringUtils.isNotEmpty(expression) && ELEMENT_FORMAL_EXPRESSION.equals(expression)) {

          List<String> assignmentList = CommaSplitter.splitCommas(xtr.getElementText());

          for (String assignmentValue : assignmentList) {
            if (assignmentValue == null) {
              continue;
            }

            assignmentValue = assignmentValue.trim();

            if (assignmentValue.length() == 0) {
              continue;
            }

            String userPrefix = "user(";
            String groupPrefix = "group(";
            if (assignmentValue.startsWith(userPrefix)) {
              assignmentValue = assignmentValue.substring(userPrefix.length(), assignmentValue.length() - 1).trim();
              ((UserTask) parentElement).getCandidateUsers().add(assignmentValue);
            } else if (assignmentValue.startsWith(groupPrefix)) {
              assignmentValue = assignmentValue.substring(groupPrefix.length(), assignmentValue.length() - 1).trim();
              ((UserTask) parentElement).getCandidateGroups().add(assignmentValue);
            } else {
              ((UserTask) parentElement).getCandidateGroups().add(assignmentValue);
            }
          }
        }
      }
    }
  }

  public class CustomIdentityLinkParser extends BaseChildElementParser {

    @Override
    public String getElementName() {
      return ELEMENT_CUSTOM_RESOURCE;
    }

    @Override
    public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
	    String identityLinkType = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_NAME);

	    // the attribute value may be unqualified
	    if (identityLinkType == null) {
	      identityLinkType = xtr.getAttributeValue(null, ATTRIBUTE_NAME);
	    }

	    if (identityLinkType == null) return;

	    String resourceElement = XMLStreamReaderUtil.moveDown(xtr);
	      if (StringUtils.isNotEmpty(resourceElement) && ELEMENT_RESOURCE_ASSIGNMENT.equals(resourceElement)) {
	        String expression = XMLStreamReaderUtil.moveDown(xtr);
	        if (StringUtils.isNotEmpty(expression) && ELEMENT_FORMAL_EXPRESSION.equals(expression)) {

	          List<String> assignmentList = CommaSplitter.splitCommas(xtr.getElementText());

	          for (String assignmentValue : assignmentList) {
	            if (assignmentValue == null) {
	              continue;
	            }

	            assignmentValue = assignmentValue.trim();

	            if (assignmentValue.length() == 0) {
	              continue;
	            }

	            String userPrefix = "user(";
	            String groupPrefix = "group(";
	            if (assignmentValue.startsWith(userPrefix)) {
	              assignmentValue = assignmentValue.substring(userPrefix.length(), assignmentValue.length() - 1).trim();
	              ((UserTask) parentElement).addCustomUserIdentityLink(assignmentValue, identityLinkType);
	            } else if (assignmentValue.startsWith(groupPrefix)) {
	              assignmentValue = assignmentValue.substring(groupPrefix.length(), assignmentValue.length() - 1).trim();
	              ((UserTask) parentElement).addCustomGroupIdentityLink(assignmentValue, identityLinkType);
	            } else {
	              ((UserTask) parentElement).addCustomGroupIdentityLink(assignmentValue, identityLinkType);
	            }
	          }
	        }
	      }
	    }
	  }
}
