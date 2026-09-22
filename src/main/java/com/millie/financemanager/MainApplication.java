package com.millie.financemanager;

import me.paulschwarz.springdotenv.spring.DotenvApplicationInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MainApplication {
    public static void main(String[] args) {

        new SpringApplicationBuilder(MainApplication.class)
                .initializers(new DotenvApplicationInitializer())
                .run(args);


        //SpringApplication.run(MainApplication.class, args);
    }
}