package cn.darkjrong.xxljob.service;

import cn.darkjrong.xxljob.domain.XxlJobGroup;

import java.util.List;

/**
 * xxl-job 组服务类
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
public interface XxlJobGroupService {

    /**
     * 获取工作组
     *
     * @return {@link List}<{@link XxlJobGroup}>
     */
    List<XxlJobGroup> getJobGroup();

    /**
     * 获取工作组
     *
     * @param appName appName
     * @return {@link List}<{@link XxlJobGroup}>
     */
    XxlJobGroup getJobGroup(String appName);

    /**
     * 注册组
     *
     * @return boolean
     */
    boolean registerGroup();

    /**
     * 精确检查
     *
     * @return boolean
     */
    boolean preciselyCheck();

}
