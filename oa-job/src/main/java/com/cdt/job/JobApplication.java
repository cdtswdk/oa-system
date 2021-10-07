package com.cdt.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/29 16:27
 * @Description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.cdt.job.mapper")
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class);
    }
}
