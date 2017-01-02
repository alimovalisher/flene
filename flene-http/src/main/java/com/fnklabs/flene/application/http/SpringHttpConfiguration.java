package com.fnklabs.flene.application.http;

import com.fnklabs.flene.application.ExecutorsConfiguration;
import com.fnklabs.flene.application.IgniteConfiguration;
import com.fnklabs.flene.application.PropertyConfiguration;
import com.fnklabs.flene.application.http.jtwig.JtwigConfig;
import com.fnklabs.flene.core.Flene;
import com.fnklabs.flene.core.FleneConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"com.fnklabs.flene.application.http"})
@Import({
        PropertyConfiguration.class,
        IgniteConfiguration.class,
        JtwigConfig.class,
        ExecutorsConfiguration.class,
        FleneConfiguration.class
})
@EnableAspectJAutoProxy
@EnableWebMvc
class SpringHttpConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringHttpConfiguration.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
