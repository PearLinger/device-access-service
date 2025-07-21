package com.elegoo.cloud.module.mqtt.access.kafka;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/11 12:24
 */

import com.elegoo.framework.mq.kafka.annotation.KafKaSubmit;
import com.elegoo.framework.mq.kafka.entity.body.KafKaBody;
import com.elegoo.framework.mq.kafka.entity.request.KafKaRequest;
import com.elegoo.framework.mq.kafka.factory.RetryFactory;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @KafkaListener(topics = {"send"}, groupId = "my-group92")
  @KafKaSubmit
  public void listenSend(ConsumerRecord<String, KafKaBody<String>> message,Acknowledgment acknowledgment) {
    System.out.println(message.value());
    System.out.println("收到来自一个主题的消息: " + message);
    // 处理消息的业务逻辑
  }

  @KafkaListener(topics = {"sendList"}, groupId = "my-group955",containerFactory = RetryFactory.BATCH_Factory)
  @KafKaSubmit
  public void listenSendList(List<ConsumerRecord<String, KafKaBody<String>>> message,Acknowledgment acknowledgment) {
    if (message.size() > 3) {
      System.out.println("收到来自多个主题的消息: " + message.size());
    }
    // 处理消息的业务逻辑
  }



}