package com.tbxx.wpct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZXX
 * @ClassName WpctApplication
 * @Description TODO
 * @DATE 2022/9/29 18:18
 */

@MapperScan("com.tbxx.wpct")
@SpringBootApplication
public class WpctApplication {
    public static void main(String[] args) {
        SpringApplication.run(WpctApplication.class, args);
    }
}
