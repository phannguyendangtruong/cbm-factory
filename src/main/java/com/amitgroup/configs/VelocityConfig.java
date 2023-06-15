package com.amitgroup.configs;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amitgroup.services.VelocityService;
import java.util.Properties;


@Configuration
public class VelocityConfig {
    @Bean
    public VelocityEngine velocityEngine() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty("runtime.log", "logs/velocity.log");
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        return velocityEngine;
    }

    @Bean
    public VelocityService velocityServiceMail(VelocityEngine velocityEngine) {
        return new VelocityService(velocityEngine);
    }
}

