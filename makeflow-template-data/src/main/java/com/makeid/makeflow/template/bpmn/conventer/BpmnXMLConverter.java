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
import com.makeid.makeflow.template.bpmn.conventer.parser.*;
import com.makeid.makeflow.template.bpmn.exceptions.XMLException;
import com.makeid.makeflow.template.bpmn.model.BaseElement;
import com.makeid.makeflow.template.bpmn.model.BpmnModel;
import com.makeid.makeflow.template.bpmn.model.Process;
import com.makeid.makeflow.template.bpmn.model.SubProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**


 */
public class BpmnXMLConverter implements BpmnXMLConstants {

  protected static final Logger LOGGER = LoggerFactory.getLogger(BpmnXMLConverter.class);

  protected static final String BPMN_XSD = "org/activiti/impl/bpmn/parser/BPMN20.xsd";
  protected static final String DEFAULT_ENCODING = "UTF-8";

  protected static Map<String, BaseBpmnXMLConverter> convertersToBpmnMap = new HashMap<String, BaseBpmnXMLConverter>();
  protected static Map<Class<? extends BaseElement>, BaseBpmnXMLConverter> convertersToXMLMap = new HashMap<Class<? extends BaseElement>, BaseBpmnXMLConverter>();

  protected ClassLoader classloader;
  protected List<String> userTaskFormTypes;
  protected List<String> startEventFormTypes;
  protected DefinitionsParser definitionsParser = new DefinitionsParser();

  protected ExtensionElementsParser extensionElementsParser = new ExtensionElementsParser();
  protected ProcessParser processParser = new ProcessParser();

  protected BpmnShapeParser bpmnShapeParser = new BpmnShapeParser();

  protected BpmnEdgeParser bpmnEdgeParser = new BpmnEdgeParser();

  static {
    // events
    addConverter(new EndEventXMLConverter());
    addConverter(new StartEventXMLConverter());

    // tasks

    addConverter(new UserTaskXMLConverter());
    addConverter(new TaskXMLConverter());


    // gateways
    addConverter(new ExclusiveGatewayXMLConverter());


    // connectors
    addConverter(new SequenceFlowXMLConverter());

  }

  public static void addConverter(BaseBpmnXMLConverter converter) {
    addConverter(converter, converter.getBpmnElementType());
  }

  public static void addConverter(BaseBpmnXMLConverter converter, Class<? extends BaseElement> elementType) {
    convertersToBpmnMap.put(converter.getXMLElementName(), converter);
    convertersToXMLMap.put(elementType, converter);
  }

  public void setClassloader(ClassLoader classloader) {
    this.classloader = classloader;
  }

  public void setUserTaskFormTypes(List<String> userTaskFormTypes) {
    this.userTaskFormTypes = userTaskFormTypes;
  }

  public void setStartEventFormTypes(List<String> startEventFormTypes) {
    this.startEventFormTypes = startEventFormTypes;
  }

  public void validateModel(XMLStreamReader xmlStreamReader) throws Exception {
    Schema schema = createSchema();

    Validator validator = schema.newValidator();
    validator.validate(new StAXSource(xmlStreamReader));
  }

  protected Schema createSchema() throws SAXException {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = null;
    if (classloader != null) {
      schema = factory.newSchema(classloader.getResource(BPMN_XSD));
    }

    if (schema == null) {
      schema = factory.newSchema(BpmnXMLConverter.class.getClassLoader().getResource(BPMN_XSD));
    }

    if (schema == null) {
      throw new XMLException("BPMN XSD could not be found");
    }
    return schema;
  }


  public BpmnModel convertToBpmnModel(XMLStreamReader xtr) {
    BpmnModel model = new BpmnModel();
    model.setStartEventFormTypes(startEventFormTypes);
    model.setUserTaskFormTypes(userTaskFormTypes);
    try {
      Process activeProcess = null;
      List<SubProcess> activeSubProcessList = new ArrayList<SubProcess>();
      while (xtr.hasNext()) {
        try {
          xtr.next();
        } catch (Exception e) {
          LOGGER.debug("Error reading XML document", e);
          throw new XMLException("Error reading XML", e);
        }

        if (xtr.isEndElement() && (BpmnXMLConstants.ELEMENT_SUBPROCESS.equals(xtr.getLocalName()) ||
                BpmnXMLConstants.ELEMENT_TRANSACTION.equals(xtr.getLocalName()) ||
                BpmnXMLConstants.ELEMENT_ADHOC_SUBPROCESS.equals(xtr.getLocalName()))) {

          activeSubProcessList.remove(activeSubProcessList.size() - 1);
        }

        if (!xtr.isStartElement()) {
          continue;
        }
        if (BpmnXMLConstants.ELEMENT_DEFINITIONS.equals(xtr.getLocalName())) {
          definitionsParser.parse(xtr, model);
        } else if (BpmnXMLConstants.ELEMENT_PROCESS.equals(xtr.getLocalName())) {
          Process process = processParser.parse(xtr, model);
          if (process != null) {
            activeProcess = process;
          }

        } else if (BpmnXMLConstants.ELEMENT_EXTENSIONS.equals(xtr.getLocalName())) {
          extensionElementsParser.parse(xtr, activeSubProcessList, activeProcess, model);

        } else if (BpmnXMLConstants.ELEMENT_DI_SHAPE.equals(xtr.getLocalName())) {
          bpmnShapeParser.parse(xtr, model);

        } else if (BpmnXMLConstants.ELEMENT_DI_EDGE.equals(xtr.getLocalName())) {
          bpmnEdgeParser.parse(xtr, model);

        } else {
          if (convertersToBpmnMap.containsKey(xtr.getLocalName())) {
            if (activeProcess != null) {
              BaseBpmnXMLConverter converter = convertersToBpmnMap.get(xtr.getLocalName());
              converter.convertToBpmnModel(xtr, model, activeProcess, activeSubProcessList);
            }
          }
        }
      }
      } catch(XMLException e){
        throw e;

      } catch(Exception e){
        LOGGER.error("Error processing BPMN document", e);
        throw new XMLException("Error processing BPMN document", e);
      }
      return model;
  }
}
