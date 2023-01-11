package cn.darkjrong.xxljob.service;

import org.junit.jupiter.api.Test;

public class XxlJobLoginServiceTest extends BaseServiceTest {

    @Test
    public void getCookie() {
        String cookie = xxlJobLoginService.getCookie();
        System.out.println(cookie);

    }


}
