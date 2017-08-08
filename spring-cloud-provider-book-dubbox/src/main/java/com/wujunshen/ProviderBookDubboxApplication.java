package com.wujunshen;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.remoting.http.servlet.BootstrapListener;
import com.wujunshen.service.BookServiceImpl;
import com.wujunshen.service.facade.BookRest4DubboServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.wujunshen.dao")
@ImportResource({"classpath:dubboContext.xml"})
@EnableDiscoveryClient
public class ProviderBookDubboxApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderBookDubboxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProviderBookDubboxApplication.class, args);
    }

    @Bean
    public ServletListenerRegistrationBean<BootstrapListener> BootstrapListener() {
        return new ServletListenerRegistrationBean<>(new com.alibaba.dubbo.remoting.http.servlet.BootstrapListener());
    }

    @Bean(name = "bookService")
    public BookServiceImpl BookServiceImpl() {
        return new BookServiceImpl();
    }

    @Bean(name = "bookRest4DubboService")
    public BookRest4DubboServiceImpl BookRest4DubboServiceImpl() {
        return new BookRest4DubboServiceImpl();
    }

    @Bean
    public ServletRegistrationBean DispatcherServlet() {
        return new ServletRegistrationBean(new com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet(), "/*");
    }
}
