package cn.darkjrong.xxljob.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 路由策略
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
@Getter
@AllArgsConstructor
public enum RouteStrategy {

    /*
    第一个：选择第一个机器
    最后一个：选择最后一个机器
    轮询：依次选择执行
    随机：随机选择在线的机器
    一致性HASH：每个任务按照Hash算法固定选择某一台机器，并且所有的任务均匀散列在不同的机器上
    最不经常使用：使用频率最低的机器优先被使用
    最近最久未使用：最久未使用的机器优先被选举
    故障转移：按照顺序依次进行心跳检测，第一个心跳检测成功的机器选定为目标的执行器并且会发起任务调度
    忙碌转移：按照顺序来依次进行空闲检测，第一个空闲检测成功的机器会被选定为目标群机器，并且会发起任务调度
    分片广播：广播触发对于集群中的所有机器执行任务，同时会系统会自动传递分片的参数
     */
    FIRST,
    LAST,
    ROUND,
    RANDOM,
    CONSISTENT_HASH,
    LEAST_FREQUENTLY_USED,
    LEAST_RECENTLY_USED,
    FAILOVER,
    BUSYOVER,
    SHARDING_BROADCAST;















    ;
}
