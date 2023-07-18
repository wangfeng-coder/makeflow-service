package com.makeid.makeflow.workflow.delegate;

import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public interface DelegateScopeWriter {

    void addVariables(Map<String,Object> variables);

}
