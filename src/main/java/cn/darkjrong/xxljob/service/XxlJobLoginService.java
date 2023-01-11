package cn.darkjrong.xxljob.service;

/**
 * xxl job 登录服务类
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
public interface XxlJobLoginService {

    /**
     * 登录
     */
    void login();

    /**
     * 获取Cookie
     *
     * @return {@link String}
     */
    String getCookie();

}
