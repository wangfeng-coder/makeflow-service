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
package com.makeid.makeflow.workflow.runtime;


import com.makeid.makeflow.workflow.operation.AtomicOperation;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.process.PvmTransition;
import com.makeid.makeflow.workflow.process.TransitionImpl;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;

import java.util.List;

public interface ActivityPvmExecution extends PvmProcessInstance {

   PvmActivity findActivityInst();



   void saveActivityInst();

    void take(PvmTransition pvmTransition);

    void take(List<? extends PvmTransition> transitionsToTake);

    void take();

    void performOperation(AtomicOperation atomicOperation);
}
