package com.elegoo.cloud.module.mqtt.access.mqtt;

import com.elegoo.cloud.module.mqtt.access.config.ElegooMqttConfig;
import com.elegoo.cloud.module.mqtt.access.service.DeviceControlService;
import com.elegoo.cloud.module.mqtt.access.util.JacksonUtil;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * mqtt消息生产者
 *
 * @author Jiaqi.X
 * @version 1.0
 * @date
 */
@Component
@Slf4j
public class MqttMessageProduce implements InitializingBean {
    @Autowired
    private ElegooMqttConfig elegooMqttConfig;

    @Autowired
    private DeviceControlService deviceControlService;

    public static List<ElegooMqttClient> elegooMqttClientList = Lists.newArrayList();

    private int initErrCount = 0;

  public  List<ElegooMqttClient> getElegooMqttClientList() {
    return elegooMqttClientList;
  }

  @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     * 初始化MQTT连接
     */
    public void init() {
            int clientNum = 5;
            //开启share订阅才能开启多个client .
            if(StringUtil.isNotEmpty(elegooMqttConfig.getBroker().getShare())){
                clientNum = elegooMqttConfig.getBroker().getClientNum();
            }
      clientNum = 5;
            for(int i = 0; i < clientNum; i ++){
                ElegooMqttClient rinoMqttClient = null;
                do{
                    try {
                        log.info("mqtt配置：{}", JacksonUtil.writeValueAsString(elegooMqttConfig.getBroker()));
                        rinoMqttClient = new ElegooMqttClient(
                            elegooMqttConfig.getBroker().getUrl(),
                            elegooMqttConfig.getBroker().getClientId(),
                            elegooMqttConfig.getBroker().getUsername(),
                            elegooMqttConfig.getBroker().getPassword(),
                            elegooMqttConfig.getBroker().getShare(),
                            deviceControlService);
                        initErrCount = 0;
                        log.info("初始化mqttClient[{}]成功...",i);
                        elegooMqttClientList.add(rinoMqttClient);
                    } catch (Exception e) {
                        log.error("初始化Mqtt客户端失败",e);
                        initErrCount ++;
                        log.info("初始化mqttClient失败，{}分钟后重试",initErrCount);
                        try {
                            Thread.sleep(initErrCount * 1000 * 60L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    }
                }while(rinoMqttClient == null);
            }

    }

//    /**
//     * 发布mqtt消息到broker
//     * @param topic
//     * @param message
//     * @param qos
//     */
//    public void sendMqttMessage(String topic,String message,int qos){
//        Random random = new Random();
//        int index = random.nextInt(rinoMqttClientList.size());
//        log.info("发送mqtt消息:topic={},message={},qos={},clientIndex={}", topic,message,qos,index);
//        if(topic.startsWith("rlink/v2/") || topic.startsWith("product/v2/") ){
//            MessageData.Head head = MessageData.parseTopic(topic);
//            ConvertContext ctx = new ConvertContext(head.getTopic(),head.getPid(),head.getUuid());
//            message = MessageConvertHandle.encode(ctx,message);
//        }
//        rinoMqttClientList.get(index).publish(topic,message,qos);
//
//    }

    /**
     * 发布mqtt消息到broker
     * @param topic
     * @param message
     */
    public void sendMqttMessage(String topic,String message){
    }

  public static void main(String[] args) throws Exception {
    // 创建ElegooMqttClient实例
    ElegooMqttClient rinoMqttClient = new ElegooMqttClient(
        "192.168.3.25:1883",
        "testsend",
        "testsend",
        "testsend",
        "$share",
        new DeviceControlService()
    );
    rinoMqttClient.getTopicList().clear();

    // 创建线程池，这里设置线程池大小为10，可根据实际情况调整
    ExecutorService executorService = new ThreadPoolExecutor(10,
        50, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100000), new ThreadPoolExecutor.CallerRunsPolicy());

    for (int i = 0; i < 500000; i++) {
      final int messageIndex = i;
      // 提交任务到线程池
      executorService.submit(() -> {
        rinoMqttClient.publish("test/s/sd", "test" + messageIndex, 0);
      });
    }

//    // 关闭线程池，等待所有任务执行完成
//    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
      }
    } catch (InterruptedException e) {
      executorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
//
//    // 关闭MQTT客户端
//    rinoMqttClient.close();
  }


}
