package com.elegoo.cloud.module.mqtt.access.kafka;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/11 12:24
 */

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

  /**
   * 使用注解订阅单个主题
   */
  @KafkaListener(topics = "test-topic", groupId = "my-group")
  public void listenSingleTopic(String message) {
    System.out.println("收到来自主题 test-topic 的消息: " + message);
    // 处理消息的业务逻辑
  }

  /**
   * 使用注解订阅多个主题
   */
  @KafkaListener(topics = {"topic1", "topic2"}, groupId = "my-group")
  public void listenMultipleTopics(String message) {
    System.out.println("收到来自多个主题的消息: " + message);
    // 处理消息的业务逻辑
  }


}