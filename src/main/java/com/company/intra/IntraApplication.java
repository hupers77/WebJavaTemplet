package com.company.intra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 사내 웹시스템 진입점.
 * - 내장 Tomcat 실행 : main()
 * - 외장 Apache Tomcat WAR 배포 : SpringBootServletInitializer 상속 필수
 */
@SpringBootApplication
public class IntraApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IntraApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(IntraApplication.class, args);
    }
}
