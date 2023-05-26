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


import com.makeid.makeflow.template.bpmn.model.ActivitiListener;
import com.makeid.makeflow.template.bpmn.model.BaseElement;
import com.makeid.makeflow.template.bpmn.model.HasExecutionListeners;
import com.makeid.makeflow.template.bpmn.model.SequenceFlow;
import org.apache.commons.lang3.StringUtils;

/**

 */
public class ExecutionListenerParser extends ActivitiListenerParser {

    public String getElementName() {
        return ELEMENT_EXECUTION_LISTENER;
    }

    public void addListenerToParent(ActivitiListener listener,
                                    BaseElement parentElement) {
        if (parentElement instanceof HasExecutionListeners) {
            if (StringUtils.isEmpty(listener.getEvent()) && parentElement instanceof SequenceFlow) {
                // No event type on a sequenceflow = 'take' implied
                listener.setEvent("take");
            }
            ((HasExecutionListeners) parentElement).getExecutionListeners().add(listener);
        }
    }
}
