package com.elderlycare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableJpaRepositories("com.elderlycare.repository")
@ComponentScan("com.elderlycare")
public class EveningSerenityApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EveningSerenityApplication.class);
        app.setWebApplicationType(org.springframework.boot.WebApplicationType.SERVLET);
        app.run(args);
    }
}