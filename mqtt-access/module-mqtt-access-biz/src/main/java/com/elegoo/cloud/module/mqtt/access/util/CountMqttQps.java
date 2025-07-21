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
//      log.info("每秒吞吐量: " + currentCount + " 条消息");
      messageCount.set(0);
    }, 1, 1, TimeUnit.SECONDS);
  }
  public void incrementMessageCount() {
    messageCount.incrementAndGet();
  }

  public static void main(String[] args) {
 }
}
