package com.elegoo.cloud.device.tbmq.response;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/19 15:47
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TbmqApiResponse<T> {
  private int status;
  private String message;
  private T data;
  private int errorCode;
  private String timestamp;

  // 成功响应构造方法
  public TbmqApiResponse(T data) {
    this.status = 200;
    this.message = "success";
    this.data = data;
    this.errorCode = 0;
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
  }

  // 失败响应构造方法
  public TbmqApiResponse(int status, String message, int errorCode) {
    this.status = status;
    this.message = message;
    this.data = null;
    this.errorCode = errorCode;
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
  }

  // Getter & Setter
  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "TbmqApiResponse{" +
        "status=" + status +
        ", message='" + message + '\'' +
        ", data=" + data +
        ", errorCode=" + errorCode +
        ", timestamp='" + timestamp + '\'' +
        '}';
  }
}