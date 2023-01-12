package cn.darkjrong.xxljob.service.impl;

import cn.darkjrong.spring.boot.autoconfigure.XxlJobProperties;
import cn.darkjrong.xxljob.domain.XxlJobGroup;
import cn.darkjrong.xxljob.enums.UrlEnum;
import cn.darkjrong.xxljob.exceptions.XxlJobException;
import cn.darkjrong.xxljob.service.XxlJobGroupService;
import cn.darkjrong.xxljob.service.XxlJobLoginService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * xxl-job组服务类实现类
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
@Slf4j
@Service
public class XxlJobGroupServiceImpl implements XxlJobGroupService {

    @Autowired
    private XxlJobLoginService xxlJobLoginService;

    @Autowired
    private XxlJobProperties xxlJobProperties;

    @Override
    public List<XxlJobGroup> getJobGroup() {
        String url = xxlJobProperties.getAdmin().getAddresses() + UrlEnum.JOB_GROUP_PAGE_LIST.getValue();

        String name = StrUtil.isEmpty(xxlJobProperties.getExecutor().getName())
                ? xxlJobProperties.getExecutor().getAppName()
                :xxlJobProperties.getExecutor().getName();
        HttpResponse response = HttpRequest.get(url)
                .form("appname", xxlJobProperties.getExecutor().getAppName())
                .form("title", name)
                .cookie(xxlJobLoginService.getCookie())
                .execute();

        return JSONArray.parseArray(JSON.toJSONString(JSON.parseObject(response.body()).get("data")), XxlJobGroup.class);
    }

    @Override
    public XxlJobGroup getJobGroup(String appName) {
        List<XxlJobGroup> xxlJobGroups = getJobGroup();
        if (CollectionUtil.isNotEmpty(xxlJobGroups)) {
            return xxlJobGroups.stream()
                    .filter(a -> StrUtil.equals(appName, a.getAppname()))
                    .findAny().orElse(null);
        }
        return null;
    }

    @Override
    public boolean registerGroup() {
        if (xxlJobProperties.getExecutor().getAddressType().equals(1)
                && CollectionUtil.isEmpty(xxlJobProperties.getExecutor().getAddresses())){
            log.error("In manual input mode, the actuator address list cannot be empty");
            throw new XxlJobException("手动录入模式下,执行器地址列表不能为空");
        }

        String url = xxlJobProperties.getAdmin().getAddresses() + UrlEnum.JOB_GROUP_SAVE.getValue();
        XxlJobGroup xxlJobGroup = getJobGroup(xxlJobProperties.getExecutor().getAppName());
        if (ObjectUtil.isNotNull(xxlJobGroup)) {
            url =  xxlJobProperties.getAdmin().getAddresses() + UrlEnum.JOB_GROUP_UPDATE.getValue();
        }

        String name = StrUtil.isEmpty(xxlJobProperties.getExecutor().getName())
                ? xxlJobProperties.getExecutor().getAppName()
                :xxlJobProperties.getExecutor().getName();
        HttpRequest httpRequest = HttpRequest.post(url)
                .form("appname", xxlJobProperties.getExecutor().getAppName())
                .form("title", name)
                .form("addressType", xxlJobProperties.getExecutor().getAddressType())
                .form("addressList", CollectionUtil.join(xxlJobProperties.getExecutor().getAddresses(), StrUtil.COMMA));

        if (ObjectUtil.isNotNull(xxlJobGroup)) {
            httpRequest.form("id", xxlJobGroup.getId());
        }

        HttpResponse response = httpRequest.cookie(xxlJobLoginService.getCookie()).execute();
        return JSON.parseObject(response.body()).getInteger("code").equals(200);
    }

    @Override
    public boolean preciselyCheck() {
        List<XxlJobGroup> jobGroup = getJobGroup();
        Optional<XxlJobGroup> has = jobGroup.stream()
                .filter(xxlJobGroup -> xxlJobGroup.getAppname().equals(xxlJobProperties.getExecutor().getAppName())
                        && xxlJobGroup.getTitle().equals(xxlJobProperties.getExecutor().getName()))
                .findAny();
        return has.isPresent();
    }

}
