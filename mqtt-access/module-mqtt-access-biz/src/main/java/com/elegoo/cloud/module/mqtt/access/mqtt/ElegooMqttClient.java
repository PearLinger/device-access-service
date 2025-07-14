package com.elegoo.cloud.module.mqtt.access.mqtt;

import com.elegoo.cloud.module.mqtt.access.mqtt.callback.ElegooMqttCallBack;
import com.elegoo.cloud.module.mqtt.access.service.DeviceControlService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.MqttTopic;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.util.StringUtils;

/**
 * mqtt Client
 *
 * @author Jiaqi.X
 */
@Slf4j
public class ElegooMqttClient {



    private MqttClient client;
    private MqttConnectionOptions conOpt;
    private final String schema = "tcp://";
    /**
     * 订阅topic
     */
    private List<String> topicList = new ArrayList<>();

    public ElegooMqttClient(String brokerUrl,
                          String clientId,
                          String userName,
                          String password,
                          String share,
        DeviceControlService deviceControlService)
            throws MqttException {

        this.conOpt =new MqttConnectionOptions();
        this.conOpt.setConnectionTimeout(60);
        this.conOpt.setKeepAliveInterval(50);
        this.conOpt.setCleanStart(true);
        this.conOpt.setUserName(userName);
        this.conOpt.setPassword(password.getBytes());
        this.conOpt.setSessionExpiryInterval(0L);
        String shareStr = StringUtils.isEmpty(share) ? "" : share;

//        topicList.add("$share/group/test/+/+/response");
        topicList.add("/test/+/+/response");
//        topicList.add("/test/#");
        //v1 topic
        this.client = new MqttClient(schema+brokerUrl,
            clientId,
            new MemoryPersistence());
        this.client.setCallback(new ElegooMqttCallBack(deviceControlService,this));
        this.connect();


//        this.conOpt =new MqttConnectionOptions();
//        this.conOpt.setConnectionTimeout(60);
//        this.conOpt.setKeepAliveInterval(50);
//        this.conOpt.setCleanStart(true);
//        this.conOpt.setUserName(userName);
//        this.conOpt.setPassword(password.getBytes());
//        this.conOpt.setSessionExpiryInterval(0L);
//        String shareStr = StringUtils.isEmpty(share) ? "" : share;
//        //v1 topic
//        //v2 topic
//        topicList.addAll(Arrays.stream(MqttTopicV2Enum.values())
//                .filter(s->!StringUtils.isEmpty(s.getProcess()))
//                .map(s->shareStr + s.getTopic()).collect(Collectors.toList()));
//        topicList.add("$share/group/elegoo/+/+/report");
//        topicList.add("device/response/#");
//        this.client = new MqttClient(schema+brokerUrl,
//                clientId,
//                new MemoryPersistence());
////        this.client.setCallback(new ElegooMqttCallBack(deviceControlService,this));
//        this.client.setCallback(new RinoMqttCallBack(this));
//        this.connect();
    }

    public List<String> getTopicList() {
        return topicList;
    }

    public void connect() {
        try {
            if (!this.client.isConnected()) {
                this.client.connect(this.conOpt);
            }
        }catch (MqttException e) {
            log.error("【mqtt连接失败】",e);
        }
    }

    public void subscribe(String[] topicNames,int[] qos) {
        try {
            this.client.subscribe(topicNames, qos);
        }catch (MqttException e) {
            log.error("【mqtt订阅失败】",e);
        }
    }

    public void publish(String topicName, String message,int qos) {
        try {
            MqttMessage mqttMessage =new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setPayload(message.getBytes());
            MqttTopic mqttTopic = this.client.getTopic(topicName);
            mqttTopic.publish(mqttMessage);
        }catch (MqttException e) {
            log.error("【mqtt消息发布失败】",e);
        }
    }

    public void close() {
        try {
            this.client.disconnect();
            this.client.close();
        }catch (MqttException e) {
            log.error("【mqtt连接关闭失败】",e);
        }
    }

    public void reConnect()throws MqttException {
        if (null !=this.client) {
            if(!this.client.isConnected()) {
                client.connect(this.conOpt);
            }else {
                this.close();
                this.client.connect(this.conOpt);
            }
        }
    }
}
