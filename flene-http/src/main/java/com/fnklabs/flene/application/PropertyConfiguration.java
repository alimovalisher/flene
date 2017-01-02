package com.fnklabs.flene.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:/default-app-config.properties", "${app.config}"})
public class PropertyConfiguration {
}