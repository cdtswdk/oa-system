package com.cdt.announcement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/29 16:27
 * @Description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.cdt.announcement.mapper")
public class AnnouncementApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnnouncementApplication.class);
    }
}
