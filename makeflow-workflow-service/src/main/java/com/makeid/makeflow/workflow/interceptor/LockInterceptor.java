package com.makeid.makeflow.workflow.interceptor;


import com.makeid.makeflow.basic.utils.SpringContextUtils;
import com.makeid.makeflow.workflow.cmd.LockCommand;
import com.makeid.makeflow.workflow.constants.ErrCodeEnum;
import com.makeid.makeflow.workflow.constants.MakeflowRedisConstants;
import com.makeid.makeflow.workflow.exception.EngineException;
import com.makeid.makeflow.workflow.utils.FlowUtils;
import com.makeid.makeflow.workflow.utils.RedisKeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 流程级锁拦截器
 *
 * @author
 */
public class LockInterceptor extends AbstractCommandInterceptor {

    private static Logger log = LoggerFactory
            .getLogger(LockInterceptor.class);

    private ThreadLocal<Lock> lockThreadLocal = new ThreadLocal<>();

    private Redisson redissonClient = SpringContextUtils.getBean(Redisson.class);


    @Override
    public <T> T execute(CommandConfig config, Command<T> command) {
        try {
            if (command instanceof LockCommand) {
                LockCommand lockCommand = (LockCommand) command;
                Long processInstanceId = lockCommand.getProcessInstanceId();    //流程id
                String opLock = lockCommand.getOpLockKey();        //其他锁key

                String lockKeyContent = Objects.nonNull(processInstanceId) ? processInstanceId.toString() : opLock;    //优先取流程级别锁
                if (StringUtils.isNotBlank(lockKeyContent)) {

                    String lockKey = MakeflowRedisConstants.REDIS_LOCK_MAKEFLOW_ENGINE_KEY_TAG.concat(lockKeyContent);
                    RLock lock = redissonClient.getLock(RedisKeyUtils.genKey(MakeflowRedisConstants.REDIS_FLOW_CHANGE_TAG,lockKey));
                    if (lock.tryLock(0,TimeUnit.MILLISECONDS)) {
                        // 获取锁成功
                        this.lockThreadLocal.set(lock);
                        if (Objects.nonNull(processInstanceId))
                            FlowUtils.markFLowChangeToCache(processInstanceId);    //标记流程发生变化，add by hqg
                    } else {
                        throw new EngineException(ErrCodeEnum.FLOW_OPERATEINT);
                    }
                }
            }
            return next.execute(config, command);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (command instanceof LockCommand) {
                Lock rLock = this.lockThreadLocal.get();
                if (Objects.nonNull(rLock)) {
                    rLock.unlock();
                }
                this.lockThreadLocal.remove();
            }
        }
    }

}
