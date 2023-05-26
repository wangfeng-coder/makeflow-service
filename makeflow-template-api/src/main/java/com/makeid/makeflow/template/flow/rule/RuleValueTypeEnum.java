package com.makeid.makeflow.template.flow.rule;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *@program
 *@description 规则值表达式类型
 *@author feng_wf
 *@create 2023/5/23
 */
public enum RuleValueTypeEnum implements Serializable {
    /**
     * juel表达式
     */
    JUEL,
    /**
     * 手动输入
     */
    MANUAL,

    /**
     * 公式
     */
    FOEMULA;

}
