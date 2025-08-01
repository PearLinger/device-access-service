package com.elegoo.cloud.obico;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/8/1 11:15
 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ImageProcessor {
  // 数据库配置
  private static final String DB_URL = "jdbc:postgresql://192.168.3.25:15432/elegoo";
  private static final String DB_USER = "user_mmijbx";
  private static final String DB_PASSWORD = "password_ifrepx";

  // API配置
  private static final String API_URL = "https://app-stg.obico.io/ent/partners/api/predict/";
  private static final OkHttpClient client = new OkHttpClient();

  // 图片文件夹路径
  private static final String IMAGE_FOLDER = "E:\\project\\炒面\\spaghetti_images";

  public static void main(String[] args) {
    File folder = new File(IMAGE_FOLDER);
    if (!folder.exists() || !folder.isDirectory()) {
      System.err.println("图片文件夹不存在或不是目录: " + IMAGE_FOLDER);
      return;
    }
    String generateTime  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    // 处理文件夹中所有图片文件
    File[] imageFiles = folder.listFiles((dir, name) ->
        name.toLowerCase().endsWith(".jpg") ||
            name.toLowerCase().endsWith(".jpeg") ||
            name.toLowerCase().endsWith(".png")
    );

    if (imageFiles == null || imageFiles.length == 0) {
      System.out.println("文件夹中没有图片文件");
      return;
    }

    for (File imageFile : imageFiles) {
      try {
        Thread.sleep(3000L);
        processImageFile(generateTime,imageFile);
      } catch (Exception e) {
        System.err.println("处理文件失败: " + imageFile.getName() + ", 错误: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  /**
   * 处理单个图片文件：解析文件名、调用API、插入数据库
   */
  private static void processImageFile(String generateTime,File imageFile) throws IOException, SQLException {
    String fileName = imageFile.getName();
    System.out.println("开始处理文件: " + fileName);

    // 1. 解析文件名获取printer_id和print_id
    String baseId = extractBaseIdWithSplit(fileName);
    if (baseId == null) {
      System.err.println("文件名格式不符合要求，跳过: " + fileName);
      return;
    }
    String printerId = baseId;
    String printId = baseId;

    // 2. 调用预测API
    String apiResponse = callPredictApi(imageFile, printerId, printId);
    if (apiResponse == null || apiResponse.isEmpty()) {
      System.err.println("API返回空结果，跳过文件: " + fileName);
      return;
    }

    // 3. 解析API响应并插入数据库
    insertIntoDatabase(generateTime,apiResponse, printerId, printId, fileName);
    System.out.println("文件处理完成: " + fileName);
  }

  /**
   * 从文件名中提取ELP_12MP_dd.MM.yyyy部分
   */
  /**
   * 使用split方法从文件名中提取ELP_12MP_dd.MM.yyyy部分
   * 文件名格式：ELP_12MP_13.12.2022_167094828120
   */
  private static String extractBaseIdWithSplit(String fileName) {
    // 先去除文件扩展名
    String nameWithoutExtension = fileName.contains(".")
        ? fileName.substring(0, fileName.lastIndexOf("."))
        : fileName;

    // 使用下划线分割
    String[] parts = nameWithoutExtension.split("_");

    // 检查分割后的部分数量是否符合预期（至少4部分）
    if (parts.length < 4) {
      log.error("文件名称错误:{}",fileName);
      return null;
    }

    // 重组前3部分：ELP + 12MP + 13.12.2022
    return parts[0] + "_" + parts[1] + "_" + parts[2];
  }

  /**
   * 调用预测API上传图片并获取结果
   */
  private static String callPredictApi(File imageFile, String printerId, String printId) throws IOException {
    // 构建multipart请求体
    RequestBody requestBody = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("img", imageFile.getName(),
            RequestBody.create(MediaType.parse("image/*"), imageFile))
        .addFormDataPart("printer_id", printerId)
        .addFormDataPart("print_id", printId)
        .build();

    // 构建请求
    Request request = new Request.Builder()
        .header("Authorization","Token elegoo_9YpRu2XaTb8MLZod3KfCVeHWq0rTxEbnYp5WgAjLtBDRUZmqVNXYuHf6OaZ3rwsUcHnPAytcqE4xKmUZp1o6dQsM8RJgv7NYtEl4m5yAX3LFWo2")
        .url(API_URL)
        .post(requestBody)
        .build();

    // 执行请求
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("API调用失败: " + response.code() + " " + response.message());
      }
      return response.body().string();
    }
  }

  /**
   * 将API返回结果插入数据库
   */
  private static void insertIntoDatabase(String generateTime,String apiResponse, String printerId, String printId, String imgFilename) throws SQLException {

    // 解析JSON响应
    com.alibaba.fastjson.JSONObject jsonResponse = JSON.parseObject(apiResponse);
    JSONObject result = jsonResponse.getJSONObject("result");
    JSONObject temporalStats = result.getJSONObject("temporal_stats");
    JSONArray detections = result.getJSONArray("detections");

    // 数据库连接
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      conn.setAutoCommit(false); // 开启事务

      try {
        // 1. 插入主表obico_api_result
        String mainSql = "INSERT INTO obico_api_result (" +
            "printer_id, print_id, img_filename, p, " +
            "ewm_mean, rolling_mean_short, rolling_mean_long, " +
            "prediction_num, prediction_num_lifetime, created_at,generate_time" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP,?) RETURNING id";

        long resultId;
        try (PreparedStatement mainStmt = conn.prepareStatement(mainSql)) {
          mainStmt.setString(1, printerId);
          mainStmt.setString(2, printId);
          mainStmt.setString(3, imgFilename);
          mainStmt.setDouble(4, result.getDouble("p"));
          mainStmt.setDouble(5, temporalStats.getDouble("ewm_mean"));
          mainStmt.setDouble(6, temporalStats.getDouble("rolling_mean_short"));
          mainStmt.setDouble(7, temporalStats.getDouble("rolling_mean_long"));
          mainStmt.setInt(8, temporalStats.getInteger("prediction_num"));
          mainStmt.setInt(9, temporalStats.getInteger("prediction_num_lifetime"));
          mainStmt.setString(10, generateTime);

          // 获取自动生成的ID
          try (ResultSet rs = mainStmt.executeQuery()) {
            rs.next();
            resultId = rs.getLong(1);
          }
        }

        // 2. 插入子表obico_result_detection
        String detectionSql = "INSERT INTO obico_result_detection (" +
            "result_id, detection_score, box_x, box_y, " +
            "box_width, box_height, sort_order" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement detectionStmt = conn.prepareStatement(detectionSql)) {
          for (int i = 0; i < detections.size(); i++) {
            JSONArray detection = detections.getJSONArray(i);
            double score = detection.getDouble(0);
            JSONArray box = detection.getJSONArray(1);

            detectionStmt.setLong(1, resultId);
            detectionStmt.setDouble(2, score);
            detectionStmt.setDouble(3, box.getDouble(0));
            detectionStmt.setDouble(4, box.getDouble(1));
            detectionStmt.setDouble(5, box.getDouble(2));
            detectionStmt.setDouble(6, box.getDouble(3));
            detectionStmt.setInt(7, i + 1); // 排序从1开始
            detectionStmt.addBatch();
          }
          detectionStmt.executeBatch(); // 批量插入提高效率
        }

        conn.commit(); // 提交事务
      } catch (SQLException e) {
        conn.rollback(); // 出错时回滚
        throw e;
      }
    }
  }
}

