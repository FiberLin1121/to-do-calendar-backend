package com.fiber.todocalendar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "lable", ignoreUnknownFields = false)
@PropertySource("classpath:config/defaultValue.properties")
@Data
public class DefaultValueProperties {
    private String firstColor;
    private String secondColor;
    private String thirdColor;
    private String fourthColor;
}
