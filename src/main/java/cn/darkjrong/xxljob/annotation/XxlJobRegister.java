package cn.darkjrong.xxljob.annotation;

import cn.darkjrong.xxljob.enums.ExpireStrategy;
import cn.darkjrong.xxljob.enums.RouteStrategy;
import cn.darkjrong.xxljob.enums.ScheduleType;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * xxl-job 注册注解
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XxlJobRegister {

    /**
     * 调度类型
     */
    ScheduleType scheduleType() default ScheduleType.CRON;

    /**
     * 调度配置，值含义取决于调度类型
     * 速度, CRON表达式
     */
    String scheduleConf();

    /**
     * 调度状态：0-停止，1-运行
     */
    int triggerStatus() default 1;

    /**
     * 任务描述
     */
    String description() default "default jobDesc";

    /**
     * 负责人
     */
    String author() default "default Author";

    /**
     * 运行模式
     */
    GlueTypeEnum glueType() default GlueTypeEnum.BEAN;

    /**
     * 报警邮件
     */
    String alarmEmail() default "";

    /**
     * 路由策略
     */
    RouteStrategy routeStrategy() default RouteStrategy.FIRST;

    /**
     * 调度过期策略
     */
    ExpireStrategy expireStrategy() default ExpireStrategy.DO_NOTHING;

    /**
     * 阻塞处理策略
     */
    ExecutorBlockStrategyEnum blockStrategy() default ExecutorBlockStrategyEnum.SERIAL_EXECUTION;

    /**
     * 任务执行超时时间，单位秒, 默认：0
     *
     */
    int timeout() default 0;

    /**
     * 失败重试次数, 默认：0
     */
    int failRetryCount() default 0;















}
