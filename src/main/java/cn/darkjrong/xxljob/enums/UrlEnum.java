package cn.darkjrong.xxljob.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * url枚举
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
@Getter
@AllArgsConstructor
public enum UrlEnum {

    // 任务组查询
    JOB_GROUP_PAGE_LIST("/jobgroup/pageList"),

    // 任务组新增
    JOB_GROUP_SAVE("/jobgroup/save"),

    // 任务组修改
    JOB_GROUP_UPDATE("/jobgroup/update"),

    // 登录
    LOGIN("/login"),

    // 任务查询
    JOB_PAGE_LIST("/jobinfo/pageList"),

    // 任务新增
    JOB_SAVE("/jobinfo/add"),






    ;

    private String value;
}
