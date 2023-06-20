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

package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.workflow.runtime.PvmProcessInstance;

import java.util.Map;

/**
 * @author
 */
public interface PvmProcessDefinition  {

	PvmProcessInstance createProcessInstanceExecution(Map<String,Object> variables);

	PvmActivity findInitial();

	FlowNode findFlowNode(String codeId);

	/**
	 * 获取流程模板数据模型
	 */
	FlowProcessTemplate findFlowProcessTemplate();

//	PvmProcessInstance createProcessInstance(PvmActivity initial);

}
