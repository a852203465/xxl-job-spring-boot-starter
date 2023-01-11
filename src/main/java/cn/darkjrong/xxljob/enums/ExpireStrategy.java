package cn.darkjrong.xxljob.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 过期策略
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
@Getter
@AllArgsConstructor
public enum ExpireStrategy {

    /**
     * 忽略
     */
    DO_NOTHING,

    /**
     * 立即执行一次
     */
    FIRE_ONCE_NOW,







    ;













}
