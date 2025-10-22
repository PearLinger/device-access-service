package com.elegoo.cloud;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;


@Slf4j
@SpringBootApplication
@MapperScan("com.elegoo.cloud.**.dao.**")
@EnableFeignClients(basePackages = {"com.elegoo.module.*.api.*","com.elegoo.cloud.*.api.*"})
@EnableAsync
public class DeviceAccessApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeviceAccessApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  MqttAccessApplication模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
