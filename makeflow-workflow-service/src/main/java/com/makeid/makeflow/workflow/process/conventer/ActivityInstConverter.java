//package com.makeid.makeflow.workflow.process.conventer;
//
//import com.makeid.makeflow.template.flow.model.activity.EndActivity;
//import com.makeid.makeflow.template.flow.model.activity.StartActivity;
//import com.makeid.makeflow.template.flow.model.base.FlowNode;
//import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author feng_wf
// * @program makeflow-service
// * @description
// * @create 2023-06-05
// */
//public abstract class ActivityInstConverter {
//
//    public static final Map<Class<? extends FlowNode>,ActivityConverter> converters = new HashMap<>();
//
//    static {
//        converters.put(StartActivity.class,new StartActivityInstConverter());
//
//        converters.put(EndActivity.class,new EndActivityInstConverter() );
//    }
//
//    /**
//     * 将节点的设计模型转换成运行时领域模型
//     * @param flowNode
//     * @return
//     */
//    public static ActivityImpl convert(FlowNode flowNode) {
//        ActivityConverter activityConverter = converters.get(flowNode.getClass());
//        return activityConverter.convert(flowNode);
//    }
//}
