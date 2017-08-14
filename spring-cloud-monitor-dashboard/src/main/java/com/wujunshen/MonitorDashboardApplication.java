package com.wujunshen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 测试步骤:
 * 1. 访问http://localhost:8005/hystrix.stream 可以查看Dashboard
 * 2. 在上面的输入框填入: http://想监控的服务:端口/hystrix.stream进行测试
 * 注意：首先要先调用一下想监控的服务的API，否则将会显示一个空的图表
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
@Slf4j
public class MonitorDashboardApplication {
    public static void main(String[] args) {
        log.info("start execute MonitorDashboardApplication....\n");
        SpringApplication.run(MonitorDashboardApplication.class, args);
        log.info("end execute MonitorDashboardApplication....\n");
    }
}
