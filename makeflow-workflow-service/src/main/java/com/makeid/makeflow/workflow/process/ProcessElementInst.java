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


import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

/**
 * common properties for process definition, activity and transition including
 * event listeners.
 * 
 * @author Tom Baeyens
 */
public class ProcessElementInst implements PvmProcessElement {

	private static final long serialVersionUID = 1L;

	protected String id = new ObjectId().toString();
	protected String name;
	protected ProcessDefinitionInst processDefinitionInst;

	public ProcessElementInst() {

	}

	public ProcessElementInst(String id, String name,
							  ProcessDefinitionInst processDefinitionInst) {
		if (StringUtils.isNotBlank(id)) {
			setId(id);
		}
		setName(name);
		setProcessDefinitionInst(processDefinitionInst);
	}

	/** finder--用于延迟加载,区别于 getter--用于数据查看 **/
	@Override
	public ProcessDefinitionInst findProcessDefinitionInst() {
		return this.processDefinitionInst;
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
	public String getName() {
		return this.name;
	}

	public ProcessDefinitionInst getProcessDefinitionInst() {
		return processDefinitionInst;
	}

	public void setProcessDefinitionInst(ProcessDefinitionInst processDefinitionInst) {
		this.processDefinitionInst = processDefinitionInst;
	}
}
