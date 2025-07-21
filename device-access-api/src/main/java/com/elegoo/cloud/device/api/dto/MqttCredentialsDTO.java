package com.elegoo.cloud.device.api.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/19 15:12
 */
@Data
public class MqttCredentialsDTO {
  private String name;
  private String clientType;
  private String credentialsType;

  // 使用FastJSON的自定义反序列化器
  @JSONField(deserializeUsing = CredentialsValueDeserializer.class)
  private CredentialsValue credentialsValueObj;
  private String credentialsValue;

  @Data
  public static class CredentialsValue {
    private String clientId;
    private String userName;
    private String password;
    private AuthRules authRules;
  }

  @Data
  public static class AuthRules {
    private List<String> pubAuthRulePatterns;
    private List<String> subAuthRulePatterns;
  }

  // FastJSON自定义反序列化器（兼容版本）
  public static class CredentialsValueDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
      // 直接获取字段值（可能是字符串类型的JSON）
      Object value = parser.parse();

      if (value instanceof String) {
        String jsonStr = (String) value;
        // 处理转义字符
        jsonStr = jsonStr.replace("\\\"", "\"");
        // 解析为CredentialsValue对象
        return JSON.parseObject(jsonStr, type);
      }

      // 如果是对象类型直接转换
      if (value instanceof CredentialsValue) {
        return (T) value;
      }

      // 其他情况尝试转换
      return JSON.parseObject(JSON.toJSONString(value), type);
    }

    @Override
    public int getFastMatchToken() {
      return 0;
    }
  }

  public static void main(String[] args) {
    String json = "{\"name\":\"sdsdasd\",\"clientType\":\"DEVICE\",\"credentialsType\":\"MQTT_BASIC\",\"credentialsValue\":\"{\\\"clientId\\\":\\\"sdsads\\\",\\\"userName\\\":\\\"sdsd\\\",\\\"password\\\":\\\"1\\\",\\\"authRules\\\":{\\\"pubAuthRulePatterns\\\":[\\\".*\\\"],\\\"subAuthRulePatterns\\\":[\\\".*\\\"]}}\"}";

    try {
      // 使用FastJSON解析JSON字符串
      MqttCredentialsDTO credentials = JSON.parseObject(json, MqttCredentialsDTO.class);

      System.out.println("Name: " + credentials.getName());
      System.out.println("Client Type: " + credentials.getClientType());
      System.out.println("Credentials Type: " + credentials.getCredentialsType());

      if (credentials.getCredentialsValue() != null) {
        System.out.println("Client ID: " + credentials.getCredentialsValueObj().getClientId());
        System.out.println("User Name: " + credentials.getCredentialsValueObj().getUserName());
        System.out.println("Password: " + credentials.getCredentialsValueObj().getPassword());

        if (credentials.getCredentialsValueObj().getAuthRules() != null) {
          System.out.println("Pub Auth Rules: " + credentials.getCredentialsValueObj().getAuthRules().getPubAuthRulePatterns());
          System.out.println("Sub Auth Rules: " + credentials.getCredentialsValueObj().getAuthRules().getSubAuthRulePatterns());
        }
      }
      System.out.println(JSONObject.toJSONString(credentials));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}