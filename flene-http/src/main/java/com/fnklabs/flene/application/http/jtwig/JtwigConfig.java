package com.fnklabs.flene.application.http.jtwig;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.spring.JtwigViewResolver;
import org.jtwig.spring.boot.config.JtwigViewResolverConfigurer;
import org.jtwig.web.servlet.JtwigRenderer;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class JtwigConfig implements JtwigViewResolverConfigurer {
    @Override
    public void configure(JtwigViewResolver viewResolver) {
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".twig");
        viewResolver.setCache(false);

        viewResolver.setRenderer(new JtwigRenderer(EnvironmentConfigurationBuilder
                                                           .configuration()
                                                           .parser().withTemplateCache(null)
                                                           .and().render().withStrictMode(true)
                                                           .and().propertyResolvers().add(Arrays.asList(new FieldAsMethodPropertyResolver()))
                                                           .and()
                                                           .build()));
    }
}
