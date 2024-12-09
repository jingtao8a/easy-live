package org.jingtao8a.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"org.jingtao8a"})
@MapperScan(basePackages = {"org.jingtao8a.mapper"})
@EnableTransactionManagement
@EnableScheduling
public class EasyLiveAdminRunApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyLiveAdminRunApplication.class, args);
    }
}