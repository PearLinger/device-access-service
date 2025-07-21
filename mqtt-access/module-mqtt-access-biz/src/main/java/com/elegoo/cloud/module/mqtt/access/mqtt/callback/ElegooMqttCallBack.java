package com.elegoo.cloud.module.mqtt.access.mqtt.callback;

import cn.hutool.core.collection.CollectionUtil;
//import com.elegoo.cloud.module.mqtt.access.kafka.KafkaProducerService;
import com.elegoo.cloud.module.mqtt.access.mqtt.ElegooMqttClient;
import com.elegoo.cloud.module.mqtt.access.service.DeviceControlService;
import com.elegoo.cloud.module.mqtt.access.util.CountMqttQps;
import com.elegoo.framework.common.util.spring.SpringUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ElegooMqttCallBack implements MqttCallback {
    private static final String FILE_PATH = "mqtt_messages.txt";

    private final DeviceControlService deviceControlService;
    private final ElegooMqttClient mqttClient;

    public ElegooMqttCallBack(DeviceControlService deviceControlService, ElegooMqttClient mqttClient) {
        this.deviceControlService = deviceControlService;
        this.mqttClient = mqttClient;

    }

    @Override
    public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {

    }

    @Override
    public void deliveryComplete(IMqttToken iMqttToken) {

    }

    @Override
    public void authPacketArrived(int i, MqttProperties mqttProperties) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
//        System.out.println("收到消息: " + topic + " - " + payload);
        // 消息计数器增加
        CountMqttQps bean = SpringUtils.getBean(CountMqttQps.class);
        bean.incrementMessageCount();
//        KafkaProducerService kafkaProducerService = SpringUtils.getBean(KafkaProducerService.class);
//        kafkaProducerService.sendMessage("test-topic", "Hello, Kafka!");

        // 处理设备响应
//        if (topic.startsWith("/device/response/")) {
//            // 从消息中提取requestId
//            if (payload != null) {
//                deviceControlService.handleDeviceResponse(payload, payload);
//            }
//        }
//        writeToFile(payload);
        // 其他主题处理逻辑...
    }
    private void writeToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(new Date());
            writer.write(timestamp + " - " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractRequestId(String payload) {
        // 实际应该使用JSON解析
        // 简单示例：假设payload包含"requestId=xxx"
        Pattern pattern = Pattern.compile("requestId=([^,]+)");
        Matcher matcher = pattern.matcher(payload);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        List<String> topicList =this.mqttClient.getTopicList();
        if(CollectionUtil.isNotEmpty(topicList)){
            int[] qos = new int[topicList.size()];
            for(int i = 0;i<topicList.size();i++){
                qos[i] = 1;
            }
            this.mqttClient.subscribe(topicList.stream().toArray(String[]::new), qos);
        }
    }




    @Override
    public void mqttErrorOccurred(MqttException exception) {
        System.err.println("MQTT错误: " + exception.getMessage());
    }

    // 其他回调方法...
}