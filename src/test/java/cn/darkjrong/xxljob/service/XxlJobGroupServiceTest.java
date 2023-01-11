package cn.darkjrong.xxljob.service;

import cn.darkjrong.xxljob.domain.XxlJobGroup;
import org.junit.jupiter.api.Test;

import java.util.List;

public class XxlJobGroupServiceTest extends BaseServiceTest {

    @Test
    void getGroups() {

        List<XxlJobGroup> xxlJobGroups = xxlJobGroupService.getJobGroup();
        System.out.println(xxlJobGroups.toString());

    }








}
