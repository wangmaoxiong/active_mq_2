package com.wmx.active_mq_2.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**系统配置类*/
@Configuration
public class SystemConfig {
    /**
     * 注册 Servlet 三大组件 之  Listner
     * 添加 ServletListenerRegistrationBean ，就相当于以前在 web.xml 中配置的 <listener></listener>标签
     */
    @Bean
    public ServletListenerRegistrationBean myListener() {
        /**ServletListenerRegistrationBean<T extends EventListener> 使用的是泛型，可以注册常见的任意监听器
         * 将自己的监听器注册进来*/
        ServletListenerRegistrationBean registrationBean =
                new ServletListenerRegistrationBean(new SystemListener());
        return registrationBean;
    }
}
