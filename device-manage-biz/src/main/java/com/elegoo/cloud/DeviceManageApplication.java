package com.elegoo.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class DeviceManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeviceManageApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  MqttAccessApplication模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
