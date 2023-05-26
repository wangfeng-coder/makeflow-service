package com.makeid.makeflow.template.bpmn.util;

import com.makeid.makeflow.template.bpmn.conventer.parser.*;
import com.makeid.makeflow.template.bpmn.model.*;
import org.apache.commons.lang3.StringUtils;
import com.makeid.makeflow.template.bpmn.constants.BpmnXMLConstants;
import javax.xml.stream.*;
import java.text.StringCharacterIterator;
import java.util.*;

public class BpmnXMLUtil implements BpmnXMLConstants {

  private static Map<String, BaseChildElementParser> genericChildParserMap = new HashMap<String, BaseChildElementParser>();

  static {
    addGenericParser(new ActivitiEventListenerParser());
    addGenericParser(new ConditionExpressionParser());
    addGenericParser(new ExecutionListenerParser());
    addGenericParser(new FieldExtensionParser());
    addGenericParser(new FormPropertyParser());
    addGenericParser(new TaskListenerParser());
  }

  private static void addGenericParser(BaseChildElementParser parser) {
    genericChildParserMap.put(parser.getElementName(), parser);
  }

  public static void addXMLLocation(BaseElement element, XMLStreamReader xtr) {
    Location location = xtr.getLocation();
    element.setXmlRowNumber(location.getLineNumber());
    element.setXmlColumnNumber(location.getColumnNumber());
  }

  public static void addXMLLocation(GraphicInfo graphicInfo, XMLStreamReader xtr) {
    Location location = xtr.getLocation();
    graphicInfo.setXmlRowNumber(location.getLineNumber());
    graphicInfo.setXmlColumnNumber(location.getColumnNumber());
  }

  public static void parseChildElements(String elementName, BaseElement parentElement, XMLStreamReader xtr, BpmnModel model) throws Exception {
    parseChildElements(elementName, parentElement, xtr, null, model);
  }

  public static void parseChildElements(String elementName, BaseElement parentElement, XMLStreamReader xtr,
      Map<String, BaseChildElementParser> childParsers, BpmnModel model) throws Exception {

    Map<String, BaseChildElementParser> localParserMap =
        new HashMap<String, BaseChildElementParser>(genericChildParserMap);
    if (childParsers != null) {
      localParserMap.putAll(childParsers);
    }

    boolean inExtensionElements = false;
    boolean readyWithChildElements = false;
    while (!readyWithChildElements && xtr.hasNext()) {
      xtr.next();
      if (xtr.isStartElement()) {
        if (ELEMENT_EXTENSIONS.equals(xtr.getLocalName())) {
          inExtensionElements = true;
        } else if (localParserMap.containsKey(xtr.getLocalName())) {
          BaseChildElementParser childParser = localParserMap.get(xtr.getLocalName());
          //if we're into an extension element but the current element is not accepted by this parentElement then is read as a custom extension element
          if (inExtensionElements && !childParser.accepts(parentElement)) {
            ExtensionElement extensionElement = BpmnXMLUtil.parseExtensionElement(xtr);
            parentElement.addExtensionElement(extensionElement);
            continue;
          }
          localParserMap.get(xtr.getLocalName()).parseChildElement(xtr, parentElement, model);
        } else if (inExtensionElements) {
          ExtensionElement extensionElement = BpmnXMLUtil.parseExtensionElement(xtr);
          parentElement.addExtensionElement(extensionElement);
        }

      } else if (xtr.isEndElement()) {
        if (ELEMENT_EXTENSIONS.equals(xtr.getLocalName())) {
          inExtensionElements = false;
        } else if (elementName.equalsIgnoreCase(xtr.getLocalName())) {
          readyWithChildElements = true;
        }
      }
    }
  }

  public static ExtensionElement parseExtensionElement(XMLStreamReader xtr) throws Exception {
    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName(xtr.getLocalName());
    if (StringUtils.isNotEmpty(xtr.getNamespaceURI())) {
      extensionElement.setNamespace(xtr.getNamespaceURI());
    }
    if (StringUtils.isNotEmpty(xtr.getPrefix())) {
      extensionElement.setNamespacePrefix(xtr.getPrefix());
    }

    for (int i = 0; i < xtr.getAttributeCount(); i++) {
      ExtensionAttribute extensionAttribute = new ExtensionAttribute();
      extensionAttribute.setName(xtr.getAttributeLocalName(i));
      extensionAttribute.setValue(xtr.getAttributeValue(i));
      if (StringUtils.isNotEmpty(xtr.getAttributeNamespace(i))) {
        extensionAttribute.setNamespace(xtr.getAttributeNamespace(i));
      }
      if (StringUtils.isNotEmpty(xtr.getAttributePrefix(i))) {
        extensionAttribute.setNamespacePrefix(xtr.getAttributePrefix(i));
      }
      extensionElement.addAttribute(extensionAttribute);
    }

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


  public static List<String> parseDelimitedList(String s) {
    List<String> result = new ArrayList<String>();
    if (StringUtils.isNotEmpty(s)) {

      StringCharacterIterator iterator = new StringCharacterIterator(s);
      char c = iterator.first();

      StringBuilder strb = new StringBuilder();
      boolean insideExpression = false;

      while (c != StringCharacterIterator.DONE) {
        if (c == '{' || c == '$') {
          insideExpression = true;
        } else if (c == '}') {
          insideExpression = false;
        } else if (c == ',' && !insideExpression) {
          result.add(strb.toString().trim());
          strb.delete(0, strb.length());
        }

        if (c != ',' || (insideExpression)) {
          strb.append(c);
        }

        c = iterator.next();
      }

      if (strb.length() > 0) {
        result.add(strb.toString().trim());
      }

    }
    return result;
  }

  public static String convertToDelimitedString(List<String> stringList) {
    StringBuilder resultString = new StringBuilder();

    if (stringList != null) {
      for (String result : stringList) {
        if (resultString.length() > 0) {
          resultString.append(",");
        }
        resultString.append(result);
      }
    }
    return resultString.toString();
  }

  /**
   * add all attributes from XML to element extensionAttributes (except blackListed).
   *
   * @param xtr
   * @param element
   * @param blackLists
   */
  public static void addCustomAttributes(XMLStreamReader xtr, BaseElement element, List<ExtensionAttribute>... blackLists) {
    for (int i = 0; i < xtr.getAttributeCount(); i++) {
      ExtensionAttribute extensionAttribute = new ExtensionAttribute();
      extensionAttribute.setName(xtr.getAttributeLocalName(i));
      extensionAttribute.setValue(xtr.getAttributeValue(i));
      if (StringUtils.isNotEmpty(xtr.getAttributeNamespace(i))) {
        extensionAttribute.setNamespace(xtr.getAttributeNamespace(i));
      }
      if (StringUtils.isNotEmpty(xtr.getAttributePrefix(i))) {
        extensionAttribute.setNamespacePrefix(xtr.getAttributePrefix(i));
      }
      if (!isBlacklisted(extensionAttribute, blackLists)) {
        element.addAttribute(extensionAttribute);
      }
    }
  }

  public static boolean isBlacklisted(ExtensionAttribute attribute, List<ExtensionAttribute>... blackLists) {
    if (blackLists != null) {
      for (List<ExtensionAttribute> blackList : blackLists) {
        for (ExtensionAttribute blackAttribute : blackList) {
          if (blackAttribute.getName().equals(attribute.getName())) {
            if (blackAttribute.getNamespace() != null && attribute.getNamespace() != null && blackAttribute.getNamespace().equals(attribute.getNamespace()))
              return true;
            if (blackAttribute.getNamespace() == null && attribute.getNamespace() == null)
              return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * 'safe' is here reflecting:
   * http://activiti.org/userguide/index.html#advanced.safe.bpmn.xml
   */
  public static XMLInputFactory createSafeXmlInputFactory() {
    XMLInputFactory xif = XMLInputFactory.newInstance();
    if (xif.isPropertySupported(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES)) {
      xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
                      false);
    }

    if (xif.isPropertySupported(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES)) {
      xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
                      false);
    }

    if (xif.isPropertySupported(XMLInputFactory.SUPPORT_DTD)) {
      xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    }
    return xif;
  }
}
