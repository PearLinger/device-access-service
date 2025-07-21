package com.elegoo.cloud.module.mqtt.access.controller;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/10 21:01
 */

import com.elegoo.cloud.module.mqtt.access.service.DeviceControlService;
import com.elegoo.framework.mq.kafka.entity.request.KafKaRequest;
import com.elegoo.framework.mq.kafka.producer.KafkaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
    import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

  private final DeviceControlService deviceControlService;

  @Autowired
  KafkaManager kafkaManager;
  @Autowired
  public DeviceController(DeviceControlService deviceControlService) {
    this.deviceControlService = deviceControlService;
  }

  @GetMapping("/send")
  public void send() {
//    kafkaManager.producerRecordSend("send",new KafKaRequest());
    kafkaManager.producerRecordSend("sendList",new KafKaRequest());
    kafkaManager.producerRecordSend("send",new KafKaRequest());
  }

  @GetMapping("/command")
  public DeferredResult<?> sendCommand() {
    DeferredResult<ResponseEntity<?>> result = new DeferredResult<>(5000L);
    // 正确接收方法返回值并进行链式调用
    DeferredResult<String> responseDeferredResult = deviceControlService.sendDeviceCommand("test", "test", 5000);
    responseDeferredResult.setResultHandler(response -> {
      result.setResult(ResponseEntity.ok(response));
    });
    responseDeferredResult.onError(ex -> {
      result.setResult(ResponseEntity.status(500).body(ex.getMessage()));
    });
    return result;
  }

  @PostMapping("/{deviceId}/command")
  public DeferredResult<?> sendCommand(
      @PathVariable String deviceId,
      @RequestBody String command) {

    DeferredResult<ResponseEntity<?>> result = new DeferredResult<>(5000L);

    deviceControlService.sendDeviceCommand(deviceId, command, 5000)
        .onError(ex -> {
          result.setResult(ResponseEntity.status(500).body(ex.getMessage()));
        });

    return result;
  }
}