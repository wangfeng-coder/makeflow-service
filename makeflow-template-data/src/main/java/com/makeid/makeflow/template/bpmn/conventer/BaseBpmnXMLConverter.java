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


import com.makeid.makeflow.template.bpmn.constants.BpmnXMLConstants;
import com.makeid.makeflow.template.bpmn.conventer.parser.BaseChildElementParser;
import com.makeid.makeflow.template.bpmn.model.Process;
import com.makeid.makeflow.template.bpmn.model.*;
import com.makeid.makeflow.template.bpmn.util.BpmnXMLUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public abstract class BaseBpmnXMLConverter implements BpmnXMLConstants {

  protected static final Logger LOGGER = LoggerFactory.getLogger(BaseBpmnXMLConverter.class);

  protected static final List<ExtensionAttribute> defaultElementAttributes = asList(new ExtensionAttribute(BpmnXMLConstants.ATTRIBUTE_ID), new ExtensionAttribute(BpmnXMLConstants.ATTRIBUTE_NAME));

  protected static final List<ExtensionAttribute> defaultActivityAttributes = asList(new ExtensionAttribute(BpmnXMLConstants.ACTIVITI_EXTENSIONS_NAMESPACE, BpmnXMLConstants.ATTRIBUTE_ACTIVITY_ASYNCHRONOUS),
      new ExtensionAttribute(BpmnXMLConstants.ACTIVITI_EXTENSIONS_NAMESPACE, BpmnXMLConstants.ATTRIBUTE_ACTIVITY_EXCLUSIVE), new ExtensionAttribute(BpmnXMLConstants.ATTRIBUTE_DEFAULT), new ExtensionAttribute(BpmnXMLConstants.ACTIVITI_EXTENSIONS_NAMESPACE,
          BpmnXMLConstants.ATTRIBUTE_ACTIVITY_ISFORCOMPENSATION));

  public void convertToBpmnModel(XMLStreamReader xtr, BpmnModel model, Process activeProcess, List<SubProcess> activeSubProcessList) throws Exception {

    String elementId = xtr.getAttributeValue(null, BpmnXMLConstants.ATTRIBUTE_ID);
    String elementName = xtr.getAttributeValue(null, BpmnXMLConstants.ATTRIBUTE_NAME);
    boolean async = parseAsync(xtr);
    boolean notExclusive = parseNotExclusive(xtr);
    String defaultFlow = xtr.getAttributeValue(null, BpmnXMLConstants.ATTRIBUTE_DEFAULT);
    BaseElement parsedElement = convertXMLToElement(xtr, model);

    if (parsedElement instanceof FlowElement) {

      FlowElement currentFlowElement = (FlowElement) parsedElement;
      currentFlowElement.setId(elementId);
      currentFlowElement.setName(elementName);

      if (currentFlowElement instanceof FlowNode) {
        FlowNode flowNode = (FlowNode) currentFlowElement;
        flowNode.setAsynchronous(async);
        flowNode.setNotExclusive(notExclusive);

        if (currentFlowElement instanceof Activity) {

          Activity activity = (Activity) currentFlowElement;
          if (StringUtils.isNotEmpty(defaultFlow)) {
            activity.setDefaultFlow(defaultFlow);
          }
        }

        if (currentFlowElement instanceof Gateway) {
          Gateway gateway = (Gateway) currentFlowElement;
          if (StringUtils.isNotEmpty(defaultFlow)) {
            gateway.setDefaultFlow(defaultFlow);
          }
        }
      }
      activeProcess.addFlowElement(currentFlowElement);
    }
  }


  protected abstract Class<? extends BaseElement> getBpmnElementType();

  protected abstract BaseElement convertXMLToElement(XMLStreamReader xtr, BpmnModel model) throws Exception;

  protected abstract String getXMLElementName();


  // To BpmnModel converter convenience methods

  protected void parseChildElements(String elementName, BaseElement parentElement, BpmnModel model, XMLStreamReader xtr) throws Exception {
    parseChildElements(elementName, parentElement, null, model, xtr);
  }

  protected void parseChildElements(String elementName, BaseElement parentElement, Map<String, BaseChildElementParser> additionalParsers, BpmnModel model, XMLStreamReader xtr) throws Exception {

    Map<String, BaseChildElementParser> childParsers = new HashMap<String, BaseChildElementParser>();
    if (additionalParsers != null) {
      childParsers.putAll(additionalParsers);
    }
    BpmnXMLUtil.parseChildElements(elementName, parentElement, xtr, childParsers, model);
  }

  @SuppressWarnings("unchecked")
  protected ExtensionElement parseExtensionElement(XMLStreamReader xtr) throws Exception {
    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName(xtr.getLocalName());
    if (StringUtils.isNotEmpty(xtr.getNamespaceURI())) {
      extensionElement.setNamespace(xtr.getNamespaceURI());
    }
    if (StringUtils.isNotEmpty(xtr.getPrefix())) {
      extensionElement.setNamespacePrefix(xtr.getPrefix());
    }

    BpmnXMLUtil.addCustomAttributes(xtr, extensionElement, defaultElementAttributes);

    boolean readyWithExtensionElement = false;
    while (!readyWithExtensionElement && xtr.hasNext()) {
      xtr.next();
      if (xtr.isCharacters() || XMLStreamReader.CDATA == xtr.getEventType()) {
        if (StringUtils.isNotEmpty(xtr.getText().trim())) {
          extensionElement.setElementText(xtr.getText().trim());
        }
      } else if (xtr.isStartElement()) {
        ExtensionElement childExtensionElement = parseExtensionElement(xtr);
        extensionElement.addChildElement(childExtensionElement);
      } else if (xtr.isEndElement() && extensionElement.getName().equalsIgnoreCase(xtr.getLocalName())) {
        readyWithExtensionElement = true;
      }
    }
    return extensionElement;
  }

  protected boolean parseAsync(XMLStreamReader xtr) {
    boolean async = false;
    String asyncString = xtr.getAttributeValue(BpmnXMLConstants.ACTIVITI_EXTENSIONS_NAMESPACE, BpmnXMLConstants.ATTRIBUTE_ACTIVITY_ASYNCHRONOUS);
    if (BpmnXMLConstants.ATTRIBUTE_VALUE_TRUE.equalsIgnoreCase(asyncString)) {
      async = true;
    }
    return async;
  }

  protected boolean parseNotExclusive(XMLStreamReader xtr) {
    boolean notExclusive = false;
    String exclusiveString = xtr.getAttributeValue(BpmnXMLConstants.ACTIVITI_EXTENSIONS_NAMESPACE, BpmnXMLConstants.ATTRIBUTE_ACTIVITY_EXCLUSIVE);
    if (BpmnXMLConstants.ATTRIBUTE_VALUE_FALSE.equalsIgnoreCase(exclusiveString)) {
      notExclusive = true;
    }
    return notExclusive;
  }

  protected boolean parseForCompensation(XMLStreamReader xtr) {
    boolean isForCompensation = false;
    String compensationString = xtr.getAttributeValue(null, BpmnXMLConstants.ATTRIBUTE_ACTIVITY_ISFORCOMPENSATION);
    if (BpmnXMLConstants.ATTRIBUTE_VALUE_TRUE.equalsIgnoreCase(compensationString)) {
      isForCompensation = true;
    }
    return isForCompensation;
  }

  protected List<String> parseDelimitedList(String expression) {
    return BpmnXMLUtil.parseDelimitedList(expression);
  }

  // To XML converter convenience methods

  protected String convertToDelimitedString(List<String> stringList) {
    return BpmnXMLUtil.convertToDelimitedString(stringList);
  }

}
