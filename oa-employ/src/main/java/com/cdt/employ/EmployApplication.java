package com.cdt.employ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/29 16:27
 * @Description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.cdt.employ.mapper")
public class EmployApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployApplication.class);
    }
}
