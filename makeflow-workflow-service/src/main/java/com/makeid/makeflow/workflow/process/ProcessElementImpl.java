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


import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

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


	public ProcessElementImpl() {

	}

	public ProcessElementImpl(ProcessDefinitionImpl processDefinition) {
		this.processDefinition = processDefinition;
	}

	public ProcessElementImpl(String codeId,
							  ProcessDefinitionImpl processDefinition) {
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
}
