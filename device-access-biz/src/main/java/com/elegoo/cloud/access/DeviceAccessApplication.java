package com.elegoo.cloud.access;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class DeviceAccessApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeviceAccessApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  MqttAccessApplication模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
