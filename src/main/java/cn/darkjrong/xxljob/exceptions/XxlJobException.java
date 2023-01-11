package cn.darkjrong.xxljob.exceptions;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * xxl-job 异常
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
public class XxlJobException extends RuntimeException {

    public XxlJobException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public XxlJobException(String message) {
        super(message);
    }

    public XxlJobException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public XxlJobException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public XxlJobException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public XxlJobException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }

}
