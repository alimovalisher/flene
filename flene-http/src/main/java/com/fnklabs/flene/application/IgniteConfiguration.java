package com.fnklabs.flene.application;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteSpring;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfiguration {
    @Bean(destroyMethod = "close")
    public Ignite ignite(@Value("${ignite.configuration_url:ignite.xml}") String configurationUrl, ApplicationContext applicationContext) throws IgniteCheckedException {
        return IgniteSpring.start(configurationUrl, applicationContext);
    }
}
