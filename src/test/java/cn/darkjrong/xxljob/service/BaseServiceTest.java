package cn.darkjrong.xxljob.service;

import cn.darkjrong.spring.boot.autoconfigure.XxlJobProperties;
import cn.darkjrong.xxljob.service.impl.XxlJobGroupServiceImpl;
import cn.darkjrong.xxljob.service.impl.XxlJobInfoServiceImpl;
import cn.darkjrong.xxljob.service.impl.XxlJobLoginServiceImpl;
import cn.hutool.core.util.ReflectUtil;
import org.junit.jupiter.api.BeforeEach;

public class BaseServiceTest {

    protected static XxlJobGroupService xxlJobGroupService;
    protected static XxlJobInfoService xxlJobInfoService;
    protected static XxlJobLoginService xxlJobLoginService;

    protected static XxlJobProperties xxlJobProperties = null;

    @BeforeEach
    public void init() {

         xxlJobProperties = new XxlJobProperties();
        xxlJobProperties.setEnabled(Boolean.TRUE);

        XxlJobProperties.XxlJobAdmin xxlJobAdmin = new XxlJobProperties.XxlJobAdmin();
        xxlJobAdmin.setAddresses("http://localhost:8080");
        xxlJobAdmin.setUsername("admin");
        xxlJobAdmin.setPassword("123456");
        xxlJobProperties.setAdmin(xxlJobAdmin);

        XxlJobProperties.XxlJobExecutor xxlJobExecutor = new XxlJobProperties.XxlJobExecutor();
        xxlJobExecutor.setAppName("xdcos-xxl-job");
        xxlJobExecutor.setName("xdcos-xxl-job");
        xxlJobExecutor.setPort(9999);
        xxlJobProperties.setExecutor(xxlJobExecutor);

        xxlJobLoginService = new XxlJobLoginServiceImpl();
        ReflectUtil.setFieldValue(xxlJobLoginService, "xxlJobProperties", xxlJobProperties);

        xxlJobGroupService = new XxlJobGroupServiceImpl();
        ReflectUtil.setFieldValue(xxlJobGroupService, "xxlJobLoginService", xxlJobLoginService);
        ReflectUtil.setFieldValue(xxlJobGroupService, "xxlJobProperties", xxlJobProperties);

        xxlJobInfoService = new XxlJobInfoServiceImpl();
        ReflectUtil.setFieldValue(xxlJobInfoService, "xxlJobLoginService", xxlJobLoginService);
        ReflectUtil.setFieldValue(xxlJobInfoService, "xxlJobProperties", xxlJobProperties);


    }




}
