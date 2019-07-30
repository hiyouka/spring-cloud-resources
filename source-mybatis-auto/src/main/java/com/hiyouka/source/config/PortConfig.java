package com.hiyouka.source.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component
public class PortConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Value("${config.port}")
    private Integer port;

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(port);
    }

}