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
import com.makeid.makeflow.template.bpmn.model.BaseElement;
import com.makeid.makeflow.template.bpmn.model.BpmnModel;
import com.makeid.makeflow.template.bpmn.model.GraphicInfo;
import com.makeid.makeflow.template.bpmn.util.BpmnXMLUtil;

import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

/**

 */
public class BpmnEdgeParser implements BpmnXMLConstants {

  public void parse(XMLStreamReader xtr, BpmnModel model) throws Exception {

    String id = xtr.getAttributeValue(null, ATTRIBUTE_DI_BPMNELEMENT);
    List<GraphicInfo> wayPointList = new ArrayList<GraphicInfo>();
    while (xtr.hasNext()) {
      xtr.next();
      if (xtr.isStartElement() && ELEMENT_DI_LABEL.equalsIgnoreCase(xtr.getLocalName())) {
        while (xtr.hasNext()) {
          xtr.next();
          if (xtr.isStartElement() && ELEMENT_DI_BOUNDS.equalsIgnoreCase(xtr.getLocalName())) {
            GraphicInfo graphicInfo = new GraphicInfo();
            BpmnXMLUtil.addXMLLocation(graphicInfo, xtr);
            graphicInfo.setX(Double.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_DI_X)).intValue());
            graphicInfo.setY(Double.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_DI_Y)).intValue());
            graphicInfo.setWidth(Double.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_DI_WIDTH)).intValue());
            graphicInfo.setHeight(Double.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_DI_HEIGHT)).intValue());
            model.addLabelGraphicInfo(id, graphicInfo);
            break;
          } else if (xtr.isEndElement() && ELEMENT_DI_LABEL.equalsIgnoreCase(xtr.getLocalName())) {
            break;
          }
        }

      } else if (xtr.isStartElement() && ELEMENT_DI_WAYPOINT.equalsIgnoreCase(xtr.getLocalName())) {
        GraphicInfo graphicInfo = new GraphicInfo();
        BpmnXMLUtil.addXMLLocation(graphicInfo, xtr);
        graphicInfo.setX(Double.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_DI_X)).intValue());
        graphicInfo.setY(Double.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_DI_Y)).intValue());
        wayPointList.add(graphicInfo);

      } else if (xtr.isEndElement() && ELEMENT_DI_EDGE.equalsIgnoreCase(xtr.getLocalName())) {
        break;
      }
    }
    model.addFlowGraphicInfoList(id, wayPointList);
  }

  public BaseElement parseElement() {
    return null;
  }
}
