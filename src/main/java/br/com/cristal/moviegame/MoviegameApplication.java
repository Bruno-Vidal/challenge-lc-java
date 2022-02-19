package br.com.cristal.moviegame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MoviegameApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviegameApplication.class, args);
    }

}
