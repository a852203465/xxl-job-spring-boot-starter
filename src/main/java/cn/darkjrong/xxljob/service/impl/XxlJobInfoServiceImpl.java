package cn.darkjrong.xxljob.service.impl;

import cn.darkjrong.spring.boot.autoconfigure.XxlJobProperties;
import cn.darkjrong.xxljob.domain.XxlJobInfo;
import cn.darkjrong.xxljob.enums.UrlEnum;
import cn.darkjrong.xxljob.exceptions.XxlJobException;
import cn.darkjrong.xxljob.service.XxlJobInfoService;
import cn.darkjrong.xxljob.service.XxlJobLoginService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * xxl-job工作信息服务类实现类
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
@Slf4j
@Service
public class XxlJobInfoServiceImpl implements XxlJobInfoService {

    @Autowired
    private XxlJobLoginService xxlJobLoginService;

    @Autowired
    private XxlJobProperties xxlJobProperties;

    @Override
    public List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler) {
        String url = xxlJobProperties.getAdmin().getAddresses() + UrlEnum.JOB_PAGE_LIST.getValue();
        HttpResponse response = HttpRequest.post(url)
                .form("jobGroup", jobGroupId)
                .form("executorHandler", executorHandler)
                .form("triggerStatus", -1)
                .cookie(xxlJobLoginService.getCookie())
                .execute();

        return JSONArray.parseArray(JSON.toJSONString(JSON.parseObject(response.body()).get("data")), XxlJobInfo.class);
    }

    @Override
    public Integer addJobInfo(XxlJobInfo xxlJobInfo) {
        String url = xxlJobProperties.getAdmin().getAddresses() + UrlEnum.JOB_SAVE.getValue();
        Map<String, Object> paramMap = BeanUtil.beanToMap(xxlJobInfo);
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(xxlJobLoginService.getCookie())
                .execute();

        if (JSON.parseObject(response.body()).getInteger("code").equals(200)) {
            return Convert.toInt(JSON.parseObject(response.body()).getInteger("content"));
        }
        throw new XxlJobException(String.format("【%s】 add jobInfo error!", xxlJobInfo.getExecutorHandler()));
    }

}
