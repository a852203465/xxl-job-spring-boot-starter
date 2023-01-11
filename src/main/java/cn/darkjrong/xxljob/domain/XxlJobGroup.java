package cn.darkjrong.xxljob.domain;

import lombok.Data;

/**
 * xxl job 组
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
@Data
public class XxlJobGroup {

    /**
     * 主键
     */
    private Integer id;

    /**
     * AppName
     */
    private String appname;

    /**
     * 名称
     */
    private String title;

    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    private Integer addressType;

    /**
     * 执行器地址列表，多地址逗号分隔(手动录入)
     */
    private String addressList;





}
