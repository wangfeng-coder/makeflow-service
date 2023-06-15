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


import com.makeid.makeflow.basic.utils.SpringContextUtils;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import com.makeid.makeflow.workflow.runtime.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * common properties for process definition, activity and transition including
 * event listeners.
 * 
 * @author Tom Baeyens
 */
public class ProcessElementImpl implements PvmProcessElement {

	private static final long serialVersionUID = 1L;

	protected String id = new ObjectId().toString();

	/**
	 * 如果是定义 这是模板codeId 如果是节点这是节点codeId
	 */
	protected String codeId;

	protected String name;
	protected ProcessDefinitionImpl processDefinition;

	protected IdGenerator idGenerator;

	public ProcessElementImpl() {

	}

	public ProcessElementImpl(String id,
							  ProcessDefinitionImpl processDefinition) {
		if (StringUtils.isNotBlank(id)) {
			setId(id);
		}
		this.processDefinition = processDefinition;
	}

	public ProcessElementImpl(String id,String codeId,
							  ProcessDefinitionImpl processDefinition) {
		if (StringUtils.isNotBlank(id)) {
			setId(id);
		}
		this.codeId = codeId;
		this.processDefinition = processDefinition;
	}

	/** finder--用于延迟加载,区别于 getter--用于数据查看 **/
	@Override
	public ProcessDefinitionImpl findProcessDefinition() {
		return this.processDefinition;
	}

	@Override
	public IdGenerator findIdGenerator() {
		//TODO 根据具体情况查找id生成器
		if (Objects.nonNull(idGenerator)) {
			return idGenerator;
		}
		return SpringContextUtils.getBean(IdGenerator.class);
	}


	@Override
	public boolean isActivity() {
		return this instanceof PvmActivity;
	}

	@Override
	public boolean isTransition() {
		return this instanceof PvmTransition;
	}

	/** setter **/
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** getter **/
	@Override
	public String getId() {
		return this.id;
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
