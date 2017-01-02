package com.fnklabs.flene.application.http;

import com.fnklabs.flene.application.SpringProfiles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@Import({
        SpringHttpConfiguration.class
})
@EnableConfigurationProperties
public class HttpServer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HttpServer.class);
        app.setAdditionalProfiles(SpringProfiles.HTTP);
        app.setWebEnvironment(true);
        app.run(args);
    }
}
