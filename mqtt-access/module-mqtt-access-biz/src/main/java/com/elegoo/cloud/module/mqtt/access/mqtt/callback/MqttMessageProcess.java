package com.elegoo.cloud.module.mqtt.access.mqtt.callback;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/10 20:17
 */
public interface MqttMessageProcess {
  void process(String topic,String message);
}
