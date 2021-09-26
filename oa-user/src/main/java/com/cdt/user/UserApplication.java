package com.cdt.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/26 14:50
 * @Description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.cdt.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}
