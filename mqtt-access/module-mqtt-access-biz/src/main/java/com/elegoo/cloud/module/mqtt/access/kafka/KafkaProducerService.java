//package com.elegoo.cloud.module.mqtt.access.kafka;
//
///**
// * @author yangyi
// * @version v 1.0
// * @date 2025/7/11 12:22
// */
//
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaProducerService {
//
//
//  private final KafkaTemplate<String, String> kafkaTemplate;
//
//  public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
//    this.kafkaTemplate = kafkaTemplate;
//  }
//
//  /**
//   * 发送消息到指定主题
//   */
//  public void sendMessage(String topic, String message) {
//    kafkaTemplate.send(topic, message);
////    System.out.println("消息已发送到主题: " + topic + ", 内容: " + message);
//  }
//
//  /**
//   * 发送带key的消息（支持消息分区）
//   */
//  public void sendMessage(String topic, String key, String message) {
//    kafkaTemplate.send(topic, key, message);
//    System.out.println("消息已发送到主题: " + topic + ", key: " + key + ", 内容: " + message);
//  }
//}
