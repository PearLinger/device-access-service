package com.elegoo.cloud.module.mqtt.access;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@Slf4j
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.elegoo.module.*.*.api.*"})
public class MqttAccessApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqttAccessApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  MqttAccessApplication模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
