package com.elegoo.cloud.device.utils;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/7/19 15:47
 */
import com.elegoo.cloud.device.api.vo.TTbmqRequestInfoVO;
import com.elegoo.cloud.device.tbmq.response.TbmqApiResponse;
import okhttp3.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpPoolUtil {
  private static final Map<String, OkHttpClient> clientMap = new HashMap<>();
  private static final OkHttpClient baseClient = new OkHttpClient.Builder()
      .connectTimeout(5, TimeUnit.SECONDS)
      .readTimeout(10, TimeUnit.SECONDS)
      .writeTimeout(10, TimeUnit.SECONDS)
      .build();
  private static OkHttpPoolUtil instance;

  private OkHttpPoolUtil() {}

  public static OkHttpPoolUtil getInstance() {
    if (instance == null) {
      synchronized (OkHttpPoolUtil.class) {
        if (instance == null) {
          instance = new OkHttpPoolUtil();
        }
      }
    }
    return instance;
  }

  private OkHttpClient getOrCreateClient(String url) {
    try {
      java.net.URL javaUrl = new java.net.URL(url);
      String host = javaUrl.getHost();
      int port = javaUrl.getPort();
      if (port == -1) {
        port = "https".equals(javaUrl.getProtocol()) ? 443 : 80;
      }
      String key = host + ":" + port;
      return clientMap.computeIfAbsent(key, k ->
          baseClient.newBuilder()
              .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
              .build()
      );
    } catch (Exception e) {
      System.err.println("解析 URL 失败，使用完整 URL 作为缓存键: " + url);
      return clientMap.computeIfAbsent(url, k -> baseClient.newBuilder().build());
    }
  }

  /**
   * GET 请求（统一返回 TbmqApiResponse）
   */
  public <T> TbmqApiResponse<T> doGet(String url) {
    OkHttpClient client = getOrCreateClient(url);
    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful() && response.code() == 200) {
        String body = response.body().string();
        // 直接将响应体作为 String 类型的数据返回
        return new TbmqApiResponse<>((T) body);
      } else {
        return new TbmqApiResponse<>(
            response.code(),
            "Such MQTT Client credentials are already created!",
            31
        );
      }
    } catch (IOException e) {
      e.printStackTrace();
      return new TbmqApiResponse<>(500, "请求异常: " + e.getMessage(), 31);
    }
  }

  public <T> TbmqApiResponse<T> doPost(String jsonBody, TTbmqRequestInfoVO tTbmqRequestInfoVO) {
    Headers pairs = Headers.of();
    Headers headers = pairs.newBuilder().add("Authorization", tTbmqRequestInfoVO.getToken()).build();
    return doPost(tTbmqRequestInfoVO.getUrl(), jsonBody, headers);
  }

  /**
   * POST 请求（统一返回 TbmqApiResponse）
   */
  public <T> TbmqApiResponse<T> doPost(String url, String jsonBody,Headers headers) {
    OkHttpClient client = getOrCreateClient(url);
    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    RequestBody requestBody = RequestBody.create(mediaType, jsonBody);
    Request request = new Request.Builder()
        .headers(headers)
        .url(url)
        .post(requestBody)
        .build();
    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful() && response.code() == 200) {
        String body = response.body().string();
        return new TbmqApiResponse<>((T) body);
      } else {
        return new TbmqApiResponse<>(
            response.code(),
            "Such MQTT Client credentials are already created!",
            31
        );
      }
    } catch (IOException e) {
      e.printStackTrace();
      return new TbmqApiResponse<>(500, "请求异常: " + e.getMessage(), 31);
    }
  }

  public void cleanIdleConnections() {
    clientMap.values().forEach(client -> client.connectionPool().evictAll());
  }
}