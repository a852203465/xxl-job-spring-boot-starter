package cn.darkjrong.xxljob.service.impl;

import cn.darkjrong.spring.boot.autoconfigure.XxlJobProperties;
import cn.darkjrong.xxljob.enums.UrlEnum;
import cn.darkjrong.xxljob.exceptions.XxlJobException;
import cn.darkjrong.xxljob.service.XxlJobLoginService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * xxl-job工作登录服务类实现类
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
@Service
public class XxlJobLoginServiceImpl implements XxlJobLoginService {

    private final Map<String, String> loginCookie = new HashMap<>();

    @Autowired
    private XxlJobProperties xxlJobProperties;

    @Override
    public void login() {
        String url = xxlJobProperties.getAdmin().getAddresses() + UrlEnum.LOGIN.getValue();

        HttpResponse response = HttpRequest.post(url)
                .form("userName", xxlJobProperties.getAdmin().getUsername())
                .form("password", xxlJobProperties.getAdmin().getPassword())
                .execute();

        List<HttpCookie> cookies = response.getCookies();
        Optional<HttpCookie> cookieOpt = cookies.stream()
                .filter(cookie -> cookie.getName().equals("XXL_JOB_LOGIN_IDENTITY"))
                .findFirst();
        if (!cookieOpt.isPresent()) throw new XxlJobException("get xxl-job cookie error!");
        String value = cookieOpt.get().getValue();
        loginCookie.put("XXL_JOB_LOGIN_IDENTITY", value);
    }

    @Override
    public String getCookie() {
        for (int i = 0; i < 3; i++) {
            String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
            if (StrUtil.isNotBlank(cookieStr)) {
                return "XXL_JOB_LOGIN_IDENTITY=" + cookieStr;
            }
            login();
        }
        throw new XxlJobException("get xxl-job cookie error!");
    }


}
