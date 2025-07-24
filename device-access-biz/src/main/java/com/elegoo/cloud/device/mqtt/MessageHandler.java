package com.elegoo.cloud.device.mqtt;

import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.elegoo.cloud.device.dto.TestDto;
import com.elegoo.framework.common.util.spring.SpringUtils;
import com.elegoo.framework.mq.mqtt.annotation.MqttSubscribe;
import com.elegoo.framework.mq.mqtt.annotation.NamedValue;
import com.elegoo.framework.mq.mqtt.annotation.Payload;
import com.elegoo.framework.mq.mqtt.publisher.MqttPublisher;
import jakarta.annotation.Resource;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/23 18:33
 */
//todo 为什么@Componet无法注入下列对象
@Component
@Slf4j
public class MessageHandler implements InitializingBean {

  @Autowired
  CountMqttQps countMqttQps;

  @Autowired
  MqttPublisher mqttPublisher;

  @Override
  public void afterPropertiesSet() throws Exception {
//    System.out.println(mqttPublisher);
  }

  /**
   * topic = test/+
   */
  @MqttSubscribe(value = "test/+/+", groups = {"tf"},clients = {"productId-deviceId444","productId-deviceId2sd2222"})
  public void sub(String topic, MqttMessage message, @Payload String payload) {
    countMqttQps.incrementMessageCount();
    mqttPublisher.send("tess/tesrt","sd");
    log.info("receive from    : {}", topic);
    log.info("message payload : {}", new String(message.getPayload(), StandardCharsets.UTF_8));
    log.info("string payload  : {}", payload);
  }

  @MqttSubscribe(value="tests/{xxx}/{id}", groups="gp")
  public void sub(String topic, @NamedValue("id") String id, @NamedValue("xxx") String xxx,@Payload TestDto testDto) {
    log.info("receive from   : {}", topic);
    log.info("named value id : {}", id);
    log.info("named value xxx : {}", xxx);
    log.info("object payload : {}", testDto);
  }


}
