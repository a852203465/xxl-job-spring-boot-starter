# xxl-job-spring-boot-starter
    xxl-job 自动注册xxl-job执行器以及任务, 并制作成Spring starter

## 1、打包

```
mvn clean install
```

## 2、项目中引入

```xml
<dependency>
    <groupId>cn.darkjrong</groupId>
    <artifactId>xxl-job-spring-boot-starter</artifactId>
    <version>2.3.1</version>
</dependency>
```

## 3、配置

配置文件application.yml

```yaml
# Xxl-Job分布式定时任务调度中心
xxl:
  job:
    # 必须为true
    enabled: true
    accessToken: ""
    admin:
      addresses: http://localhost:8080
      username: admin
      password: 123456

    executor:
      appName: xdcos-xxl-job
      address: ""
      host: ""
      port: 9999
      logPath: ./logs
      logRetentionDays: 7
```

`XxlJobSpringExecutor`参数配置与之前相同

## 4、添加注解
需要自动注册的方法添加注解`@XxlJobRegister`，不加则不会自动注册

```java
@Service
public class TestService {

    @XxlJob(value = "testJob")
    @XxlJobRegister(scheduleConf = "0 0 0 * * ? *",
            author = "hydra",
            jobDesc = "测试job")
    public void testJob(){
        System.out.println("#公众号");
    }


    @XxlJob(value = "testJob222")
    @XxlJobRegister(scheduleConf = "59 1-2 0 * * ?",
            triggerStatus = 1)
    public void testJob2(){
        System.out.println("#作者");
    }

    @XxlJob(value = "testJob444")
    @XxlJobRegister(scheduleConf = "59 59 23 * * ?")
    public void testJob4(){
        System.out.println("hello xxl job");
    }
}
```

