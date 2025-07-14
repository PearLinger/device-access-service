package com.elegoo.cloud.module.mqtt.access.mqtt;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * topic 2.0枚举
 */
@AllArgsConstructor
@Getter
public enum MqttTopicV2Enum {

    /**
     * topic 约定
     * 有process代表是设备publish,允许云端订阅,允许web端在线调试订阅；允许设备端publish(只能publish当前设备的消息)，允许云端模拟虚拟设备publish
     * 无process代表云端publish，仅允许云端publishi，elegoo/product开头允许设备订阅，app开头允许APP订阅
     * ack消息回复topic格式为 请求topic + "_response"
     */

    /**
     * 设备topic格式说明：elegoo/${pid}/${uuid}/xxxxxxxx
     */
//    DEVICE_ON_LINE("elegoo/+/+/online","设备上线通知","onlineMessageProcess"),
//    DEVICE_OFF_LINE("elegoo/+/+/offline","设备离线通知","offlineMessageProcess"),
    DEVICE_REPORT("elegoo/+/+/report","设备事件上报","reportMessageProcess"),
    DEVICE_REPORT_RESPONSE("elegoo/+/+/report_response","设备事件上报回复",null),
    DEVICE_ISSUE("elegoo/+/+/issue","下发设备指令",null),
    DEVICE_ISSUE_RESPONSE("elegoo/+/+/issue_response","下发设备指令回复","issueResponseMessageProcess"),

    /**
     * 产品 topic 格式说明：product/${pid}/xxx
     */
    PRODUCT_ISSUE("product/+/issue","下发产品级指令",null),
    PRODUCT_ISSUE_RESPONSE("product/+/issue_response","下发产品级指令回复","issueResponseMessageProcess"),

    /**
     * 动态注册三元组 topic 格式说明：register/${pid}/${unique}/xxx
     */
    REGISTER("register/+/+/register","动态注册","reportMessageProcess"),

    REGISTER_RESPONSE("register/+/+/register_response","动态注册回复",null),

    /**
     * APP topic 格式说明：app/${assetId}/xxxx
     */
    APP_NOTIFY("app/+/notify","APP消息通知",null),
    APP_USER_NOTIFY("app/+/userNotify","APP用户消息通知",null),

    /**
     * 第三方扫码确认登录通知 topic 格式说明：temp/${token}/app_qr_login
     */
    TEMP_QR_LOGIN_NOTIFY("temp/+/app_qr_login","第三方扫码确认登录通知",null),


    CUSTOM_CONVERSATION("custom/conversation/+","客服会话",null),
    ;



    private final String topic;
    private final String desc;
    private final String process;

    public static String formatTopic(MqttTopicV2Enum topicV2Enum,String... params){
        String topic = topicV2Enum.getTopic();
        topic = topic.replaceAll("\\+", "%s");
        return String.format(topic, params);
    }

    public static MqttTopicV2Enum matchesTopic(String topic) {
        return Arrays.stream(MqttTopicV2Enum.values()).filter(x -> {
            String value = x.topic;
            if (value.startsWith("$")) {
                value = "\\" + value;
            }
            String regex = value
                    .replaceAll("/", "\\\\/")
                    .replaceAll("\\+", "[^/]+")
                    .replaceAll("#", "(.+)") + "$";
            Pattern p= Pattern.compile(regex);
            Matcher m=p.matcher(topic);
            return  m.matches();
        }).findFirst().orElse(null);
    }




}
