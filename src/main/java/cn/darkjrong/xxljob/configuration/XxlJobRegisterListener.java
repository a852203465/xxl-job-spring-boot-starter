package cn.darkjrong.xxljob.configuration;

import cn.darkjrong.spring.boot.autoconfigure.XxlJobProperties;
import cn.darkjrong.xxljob.annotation.XxlJobRegister;
import cn.darkjrong.xxljob.domain.XxlJobGroup;
import cn.darkjrong.xxljob.domain.XxlJobInfo;
import cn.darkjrong.xxljob.exceptions.XxlJobException;
import cn.darkjrong.xxljob.service.XxlJobGroupService;
import cn.darkjrong.xxljob.service.XxlJobInfoService;
import cn.hutool.core.util.ObjectUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * xxl-job 注册侦听器
 *
 * @author Rong.Jia
 * @date 2023/01/11
 */
@Slf4j
@Component
public class XxlJobRegisterListener implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private XxlJobGroupService jobGroupService;

    @Autowired
    private XxlJobInfoService jobInfoService;

    @Autowired
    private XxlJobProperties xxlJobProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (xxlJobProperties.isAutoRegister()) {
            addJobGroup();
            addJobInfo();
        }
    }

    private void addJobGroup() {
        if (jobGroupService.preciselyCheck())
            return;

        if(jobGroupService.registerGroup())
            log.info("register xxl-job group success!");
    }

    private void addJobInfo() {
        XxlJobGroup xxlJobGroup = jobGroupService.getJobGroup(xxlJobProperties.getExecutor().getAppName());
        if (ObjectUtil.isNull(xxlJobGroup)) {
            if (!jobGroupService.registerGroup()) {
                log.error("register xxl-job group error!");
                throw new XxlJobException("register xxl-job group error!");
            }
            xxlJobGroup = jobGroupService.getJobGroup(xxlJobProperties.getExecutor().getAppName());
        }

        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            Map<Method, XxlJob> annotatedMethods  = MethodIntrospector.selectMethods(bean.getClass(),
                    (MethodIntrospector.MetadataLookup<XxlJob>) method -> AnnotatedElementUtils.findMergedAnnotation(method, XxlJob.class));

            for (Map.Entry<Method, XxlJob> methodXxlJobEntry : annotatedMethods.entrySet()) {
                Method executeMethod = methodXxlJobEntry.getKey();
                XxlJob xxlJob = methodXxlJobEntry.getValue();

                //自动注册
                if (executeMethod.isAnnotationPresent(XxlJobRegister.class)) {
                    XxlJobRegister xxlRegister = executeMethod.getAnnotation(XxlJobRegister.class);
                    List<XxlJobInfo> jobInfo = jobInfoService.getJobInfo(xxlJobGroup.getId(), xxlJob.value());
                    if (!jobInfo.isEmpty()){
                        //因为是模糊查询，需要再判断一次
                        Optional<XxlJobInfo> first = jobInfo.stream()
                                .filter(xxlJobInfo -> xxlJobInfo.getExecutorHandler().equals(xxlJob.value()))
                                .findFirst();
                        if (first.isPresent())
                            continue;
                    }

                    XxlJobInfo xxlJobInfo = createXxlJobInfo(xxlJobGroup, xxlJob, xxlRegister);
                    jobInfoService.addJobInfo(xxlJobInfo);
                    log.info("register xxl-job 【{}】 success!", xxlJobInfo.getExecutorHandler());
                }
            }
        }
    }

    private XxlJobInfo createXxlJobInfo(XxlJobGroup xxlJobGroup, XxlJob xxlJob, XxlJobRegister xxlRegister){
        XxlJobInfo xxlJobInfo=new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroup.getId());
        xxlJobInfo.setJobDesc(xxlRegister.description());
        xxlJobInfo.setAuthor(xxlRegister.author());
        xxlJobInfo.setScheduleType(xxlRegister.scheduleType().name());
        xxlJobInfo.setScheduleConf(xxlRegister.scheduleConf());
        xxlJobInfo.setGlueType(xxlRegister.glueType().name());
        xxlJobInfo.setExecutorHandler(xxlJob.value());
        xxlJobInfo.setAlarmEmail(xxlRegister.alarmEmail());
        xxlJobInfo.setExecutorRouteStrategy(xxlRegister.routeStrategy().name());
        xxlJobInfo.setMisfireStrategy(xxlRegister.expireStrategy().name());
        xxlJobInfo.setExecutorBlockStrategy(xxlRegister.blockStrategy().name());
        xxlJobInfo.setExecutorTimeout(xxlRegister.timeout());
        xxlJobInfo.setExecutorFailRetryCount(xxlRegister.failRetryCount());
        xxlJobInfo.setGlueRemark("GLUE代码初始化");
        xxlJobInfo.setTriggerStatus(xxlRegister.triggerStatus());

        return xxlJobInfo;
    }




















}
