package ru.doronin.fibonacci.springdriven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class SpringDrivenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDrivenApplication.class, args);
    }

}
