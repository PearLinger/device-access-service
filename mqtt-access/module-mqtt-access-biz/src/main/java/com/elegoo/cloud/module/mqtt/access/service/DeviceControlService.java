package com.elegoo.cloud.module.mqtt.access.service;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/10 21:02
 */

import com.elegoo.cloud.module.mqtt.access.mqtt.ElegooMqttClient;
import com.elegoo.cloud.module.mqtt.access.mqtt.MqttMessageProduce;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.elegoo.cloud.module.mqtt.access.mqtt.ElegooMqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DeviceControlService {

  @Autowired
  private MqttMessageProduce produce;

  private  Map<String, DeferredResult<String>> pendingRequests = new ConcurrentHashMap<>();


  public DeviceControlService() {
    // 订阅响应主题
  }

  public DeferredResult<String> sendDeviceCommand(String deviceId, String command, long timeoutMs) {
    String requestId = "test";
    DeferredResult<String> result = new DeferredResult<>(timeoutMs);

    // 构建请求消息
    String payload = buildCommandPayload(requestId, deviceId, command);

    // 注册请求
    pendingRequests.put(requestId, result);

    // 设置超时处理
    result.onTimeout(() -> {
      pendingRequests.remove(requestId);
      result.setErrorResult("Request timed out");
    });

    // 发布MQTT消息
    produce.getElegooMqttClientList().get(0).publish("/device/command/" + deviceId, payload, 1);

    return result;
  }

  private String buildCommandPayload(String requestId, String deviceId, String command) {
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("requestId", requestId);
    payloadMap.put("deviceId", deviceId);
    payloadMap.put("command", command);
    payloadMap.put("timestamp", System.currentTimeMillis());
    return payloadMap.toString(); // 实际应该使用JSON序列化
  }


  public void handleDeviceResponse(String requestId, String response) {
    DeferredResult<String> result = pendingRequests.remove(requestId);
    if (result != null) {
      result.setResult(response);
    }
  }
}