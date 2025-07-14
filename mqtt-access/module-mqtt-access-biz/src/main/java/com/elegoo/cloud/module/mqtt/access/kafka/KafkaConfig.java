//package com.elegoo.cloud.module.mqtt.access.kafka;
//
///**
// * @author yangyi
// * @version v 1.0
// * @date 2025/7/11 12:19
// */
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//  // 生产者配置
//  @Bean
//  public ProducerFactory<String, String> producerFactory() {
//    Map<String, Object> configProps = new HashMap<>();
//    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.25:32770");
//    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    // 其他配置（可选）
//    configProps.put(ProducerConfig.ACKS_CONFIG, "all"); // 所有副本确认
//    return new DefaultKafkaProducerFactory<>(configProps);
//  }
//
//  @Bean
//  public KafkaTemplate<String, String> kafkaTemplate() {
//    return new KafkaTemplate<>(producerFactory());
//  }
//
//  // 消费者配置
//  @Bean
//  public ConsumerFactory<String, String> consumerFactory() {
//    Map<String, Object> configProps = new HashMap<>();
//    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.25:32770");
//    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
//    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//    // 其他配置（可选）
//    configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//    return new DefaultKafkaConsumerFactory<>(configProps);
//  }
//
//  @Bean
//  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
//    ConcurrentKafkaListenerContainerFactory<String, String> factory =
//        new ConcurrentKafkaListenerContainerFactory<>();
//    factory.setConsumerFactory(consumerFactory());
//    factory.setConcurrency(3); // 设置并发消费者数量
//    factory.getContainerProperties().setPollTimeout(3000);
//    return factory;
//  }
//}