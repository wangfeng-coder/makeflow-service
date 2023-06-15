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


public interface PvmExecution extends PvmScope {

	/** 三个动作 **/
	void start();

	void execute();
	
	void end();
	
	/**节点停留,即节点待审批人审批（未自动审批、自动跳过、挂起）**/
	void stay();

	/** 状态转换 **/
	void suspend(String message);

	void activate();

	void inactivate();


	boolean isSuspended();


	boolean isEnded();

	String getProcessInstanceId();

}
