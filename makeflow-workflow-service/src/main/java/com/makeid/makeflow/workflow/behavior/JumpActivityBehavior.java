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

package com.makeid.makeflow.workflow.behavior;


import com.makeid.makeflow.workflow.constants.MakeFlowConstants;
import com.makeid.makeflow.workflow.process.Condition;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.process.TransitionImpl;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JumpActivityBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory
            .getLogger(JumpActivityBehavior.class);

    protected void performOutgoingBehavior(ActivityPvmExecution execution) {
        PvmActivity activity = execution.findActivityInst();
        List<TransitionImpl> transitionsToTake = new ArrayList<>();
        List<TransitionImpl> outgoingTransitions = (List) activity.findOutgoingTransitions();
        for (TransitionImpl outgoingTransition : outgoingTransitions) {
            Condition condition = outgoingTransition.findCondition();
            if (Objects.isNull(condition)) {
                transitionsToTake.add(outgoingTransition);
            } else {
                if (condition.evaluate(execution)) {
                    transitionsToTake.add(outgoingTransition);
                }
            }
        }
        if (transitionsToTake.size() == 1) {
            execution.take(transitionsToTake.get(0));
        } else if (transitionsToTake.size() >= 1) {
            execution.take(transitionsToTake);

        } else {
            // 处理defaultSequenceFlow
            TransitionImpl defaultOutgoingTransition = activity
                    .findDefaultOutgoingTransition();
            if (defaultOutgoingTransition != null) {
                execution.take(defaultOutgoingTransition);
            } else {
                execution.suspend(MakeFlowConstants.SUSPEND_REASON_OUTGOING_NOT_FOUND);
                log.info("activity:{} 没有满足条件的transition", activity.getId());
            }
        }
    }

}
