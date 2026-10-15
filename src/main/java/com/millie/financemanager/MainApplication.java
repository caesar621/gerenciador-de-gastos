package com.millie.financemanager;

import me.paulschwarz.springdotenv.spring.DotenvApplicationInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {

        new SpringApplicationBuilder(MainApplication.class)
                .initializers(new DotenvApplicationInitializer())
                .run(args);

    }
}