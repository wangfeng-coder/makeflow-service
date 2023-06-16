package com.makeid.makeflow.workflow.utils;

import com.makeid.makeflow.basic.utils.RedisUtils;
import com.makeid.makeflow.workflow.constants.MakeflowRedisConstants;

import java.util.concurrent.TimeUnit;


public class FlowUtils {

	/**
	 * 标记流程发生改变
	 * @param flowInstId
	 */
	public static void markFLowChangeToCache(String flowInstId) {
		RedisUtils.setEx(RedisKeyUtils.genKey(MakeflowRedisConstants.REDIS_LOCK_MAKEFLOW_ENGINE_KEY_TAG, flowInstId), flowInstId, 12, TimeUnit.SECONDS);
	}
}
