package com.hiyouka.source.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */

@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChat {

    private String application;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}