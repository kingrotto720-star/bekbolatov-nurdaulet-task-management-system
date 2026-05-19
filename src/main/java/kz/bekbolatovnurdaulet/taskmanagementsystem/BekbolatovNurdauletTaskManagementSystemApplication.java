package kz.bekbolatovnurdaulet.taskmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BekbolatovNurdauletTaskManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BekbolatovNurdauletTaskManagementSystemApplication.class, args);
    }
}
