package cn.darkjrong.xxljob.service;

import cn.darkjrong.xxljob.domain.XxlJobInfo;

import java.util.List;

/**
 * xxl-job 信息服务类
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
public interface XxlJobInfoService {

    /**
     * 获取工作信息
     *
     * @param jobGroupId      工作组ID
     * @param executorHandler 遗嘱执行人处理程序
     * @return {@link List}<{@link XxlJobInfo}>
     */
    List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler);

    /**
     * 添加工作信息
     *
     * @param xxlJobInfo xxl工作信息
     * @return {@link Integer}
     */
    Integer addJobInfo(XxlJobInfo xxlJobInfo);

}
