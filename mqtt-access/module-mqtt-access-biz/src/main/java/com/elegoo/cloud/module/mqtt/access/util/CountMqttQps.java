package com.elegoo.cloud.module.mqtt.access.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/11 15:56
 */
@Service
@Slf4j
public class CountMqttQps implements InitializingBean {
//  每秒打印一次QPS
private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  private AtomicInteger messageCount = new AtomicInteger(0);
  @Override
  public void afterPropertiesSet() throws Exception {
    scheduler.scheduleAtFixedRate(() -> {
      int currentCount = messageCount.get();
      log.info("每秒吞吐量: " + currentCount + " 条消息");
      messageCount.set(0);
    }, 1, 1, TimeUnit.SECONDS);
  }
  public void incrementMessageCount() {
    messageCount.incrementAndGet();
  }

  public static void main(String[] args) {
    int k = 16115+77919+124383+141168+102690+136775+94111+150119+109665+46867;
    System.out.println(k);
//    : 每秒吞吐量: 16115 条消息
//    2025-07-11T16:07:01.171+08:00  INFO 21872 --- [mqtt-access-server] [pool-3-thread-1] c.e.c.m.mqtt.access.util.CountMqttQps    : 每秒吞吐量: 77919 条消息
//    2025-07-11T16:07:02.179+08:00  INFO 21872 --- [mqtt-access-server] [pool-3-thread-1] c.e.c.m.mqtt.access.util.CountMqttQps    : 每秒吞吐量: 124383 条消息
//    2025-07-11T16:07:03.171+08:00  INFO 21872 --- [mqtt-access-server] [pool-3-thread-1] c.e.c.m.mqtt.access.util.CountMqttQps    : 每秒吞吐量: 141168 条消息
//    2025-07-11T16:07:04.178+08:00  INFO 21872 --- [mqtt-access-server] [pool-3-thread-1] c.e.c.m.mqtt.access.util.CountMqttQps    : 每秒吞吐量: 102690 条消息
//    2025-07-11T16:07:05.170+08:00  INFO 21872 --- [mqtt-access-server] [pool-3-thread-1] c.e.c.m.mqtt.access.util.CountMqttQps    : 每秒吞吐量: 136775 条消息
//    2025-07-11T16:07:06.175+08:00  INFO 21872 --- [mqtt-access-server] [pool-3-thread-1] c.e.c.m.mqtt.access.util.CountMqttQps    : 每秒吞吐量: 94111 条消息
//    2025-07-11T16:07:07.180+08:00  INFO 21872 --- [mqtt-access-server] [pool-3-thread-1] c.e.c.m.mqtt.access.util.CountMqttQps    : 每秒吞吐量: 150119 条消息
//    2025-07-11T16:07:08.170+08:00  INFO 21872 --- [mqtt-access-server] [pool-3-thread-1] c.e.c.m.mqtt.access.util.CountMqttQps    : 每秒吞吐量: 109665 条消息
//    2025-07-11T16:07:09.170+08:00  INFO 21872 --- [mqtt-access-server] [pool-3-thread-1] c.e.c.m.mqtt.access.util.CountMqttQps    : 每秒吞吐量: 46867 条消息
  }
}
