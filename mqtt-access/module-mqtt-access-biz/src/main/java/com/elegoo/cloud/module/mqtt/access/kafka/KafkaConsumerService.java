package com.elegoo.cloud.module.mqtt.access.kafka;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/11 12:24
 */

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

  /**
   * 使用注解订阅单个主题
   */
  @KafkaListener(topics = "tbmq.client.session", groupId = "my-group")
  public void listenWithRecord(ConsumerRecord<String, String> record) {
    String key = record.key();  // 获取消息的 key
    String value = record.value();  // 获取消息的 value

    System.out.println("收到消息 - Key: " + key);
    System.out.println("收到消息 - Value: " + value);
    System.out.println("收到消息 - 分区: " + record.partition());
    System.out.println("收到消息 - 偏移量: " + record.offset());

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