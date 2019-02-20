package jpa.starter.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TestJpaStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestJpaStarterApplication.class, args);
    }

}
