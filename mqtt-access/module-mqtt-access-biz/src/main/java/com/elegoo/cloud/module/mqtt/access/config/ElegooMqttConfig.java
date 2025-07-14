package com.elegoo.cloud.module.mqtt.access.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("elegoo")
public class ElegooMqttConfig {

    private Broker broker;

    private LowPower lowPower;

    private Log log;

    private Boolean updateGroupShadow = false;

    @Data
    public static class Broker {
        private String url;
        private String clientId;
        private Integer clientNum = 1;
        private String username;
        private String password;
        private String share;
    }

    @Data
    public static class LowPower {
        private Integer interval;
        private Integer idle;
        private String tcpUrl;
    }

    @Data
    public static class Log {
        private Long openTime;
        private String uploadUrl;
    }
}
