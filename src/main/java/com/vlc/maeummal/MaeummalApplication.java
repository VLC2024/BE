package com.vlc.maeummal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MaeummalApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaeummalApplication.class, args);
    }

}
