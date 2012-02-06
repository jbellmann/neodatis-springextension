package org.springextensions.neodatis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "org.springextensions.neodatis")
public class AppConfig {

    /**
     * Properties to support the 'standard' mode of operation.
     */
    @Configuration
    @Profile("standard")
    @PropertySource("classpath:application.properties")
    static class Standard {
    }

}
