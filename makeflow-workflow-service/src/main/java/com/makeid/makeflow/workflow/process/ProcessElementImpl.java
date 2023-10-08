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
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import org.springframework.util.Assert;

/**
 * common properties for process definition, activity and transition including
 * event listeners.
 * 
 * @author Tom Baeyens
 */
public class ProcessElementImpl implements PvmProcessElement {

	private static final long serialVersionUID = 1L;
	/**
	 * 如果是定义 这是模板codeId 如果是节点这是节点codeId
	 */
	protected String codeId;

	protected String name;
	protected ProcessDefinitionImpl processDefinition;


	/**
	 *
	 * @param processDefinition
	 */
	public ProcessElementImpl(ProcessDefinitionImpl processDefinition) {
		this.processDefinition = processDefinition;
		this.codeId = processDefinition.getCodeId();
		this.name = processDefinition.getName();
	}

	/**
	 * 模板中的元素 构造器
	 * @param codeId
	 * @param processDefinition
	 */
	public ProcessElementImpl(String codeId,
							  ProcessDefinitionImpl processDefinition) {
		Assert.notNull(processDefinition);
		Assert.notNull(codeId);
		this.codeId = codeId;
		this.processDefinition = processDefinition;
	}

	/** finder--用于延迟加载,区别于 getter--用于数据查看 **/
	@Override
	public ProcessDefinitionImpl findProcessDefinition() {
		return this.processDefinition;
	}


	@Override
	public boolean isActivity() {
		return this instanceof PvmActivity;
	}

	@Override
	public boolean isTransition() {
		return this instanceof PvmTransition;
	}



	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String getCodeId() {
		return codeId;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public ProcessDefinitionImpl getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinitionImpl processDefinition) {
		this.processDefinition = processDefinition;
	}

	public FlowNode findFlowNode(String codeId) {
		return this.processDefinition.findFlowNode(codeId);
	}
}
