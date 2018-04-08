package com.xiaoyu.lingdian.job;

import java.util.Date;

import com.xiaoyu.lingdian.core.cache.model.RedisKeyAndValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.dangdang.ddframe.job.api.AbstractOneOffElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.xiaoyu.lingdian.core.cache.service.RedisCacheService;
import com.xiaoyu.lingdian.tool.DateUtil;

/**
 * 定时任务基类
 *
 * @author: zhangyu
 * @since: 2017/8/8
 */
public abstract class BaseSchedule extends AbstractOneOffElasticJob {

    private final Logger logger = LoggerFactory.getLogger("BASE_LOGGER");

    @Autowired
    private RedisCacheService redisCacheService;

    private static final String ISRUNNIN_KEY = "isRunning";
    private static final String LAST_RUNTIME_KEY = "lastRunTime";

    public BaseSchedule() {
    }

    public void process(JobExecutionMultipleShardingContext shardingContext) {
        String scheduleName = this.getScheduleClazz().getName();
        Date lastRunTime = new Date();
        Boolean isRunning = this.getRunning();
        if (isRunning.booleanValue()) {
            this.logger.warn(scheduleName + "定时任务正在运行中");
        } else {
            try {
                this.logger.info(scheduleName + "定时任务开始执行...");
                this.updateRunState();
                this.excute(shardingContext);
                this.updateLastRunTime(lastRunTime);
                this.logger.info(scheduleName + "定时任务执行成功");
            } catch (Exception e) {
                this.logger.error(scheduleName + "定时任务执行失败!", e);
                throw e;
            } finally {
                this.completeRunning();
            }
        }
    }

    public abstract void excute(JobExecutionMultipleShardingContext var1);

    private Class<?> getScheduleClazz() {
        return this.getClass();
    }

    private Boolean getRunning() {
        String isRunning = this.redisCacheService.get(this.getIsRunningType()) == null ? null : this.redisCacheService.get(this.getIsRunningType()).getValue();
        return isRunning == null ? Boolean.valueOf(false) : Boolean.valueOf(isRunning.toString());
    }

    private void updateRunState() {
        this.redisCacheService.add(new RedisKeyAndValue(this.getIsRunningType(), "true", 600l));
    }

    private void completeRunning() {
        this.redisCacheService.add(new RedisKeyAndValue(this.getIsRunningType(), "false", 600l));
    }

    private void updateLastRunTime(Date lastRunTime) {
        this.redisCacheService.add(new RedisKeyAndValue(this.getLastTimeType(), lastRunTime.toString(), 2147483647l));
    }

    protected Date getLastRunTime() {
        String lastRunTime = this.redisCacheService.get(this.getLastTimeType()) == null ? null : this.redisCacheService.get(this.getLastTimeType()).getValue();
        return lastRunTime == null ? null : DateUtil.parseDefaultDate(lastRunTime);
    }

    protected String getIsRunningType() {
        return "task:" + ISRUNNIN_KEY + this.getScheduleClazz().getSimpleName();
    }

    protected String getLastTimeType() {
        return "task:" + LAST_RUNTIME_KEY + this.getScheduleClazz().getSimpleName();
    }

}
