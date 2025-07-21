package com.elegoo.cloud.device.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangyi
 * @since 2025-07-19
 */
@Data
@TableName("t_tbmq_request_info")
public class TTbmqRequestInfo extends Model<TTbmqRequestInfo> {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Long id;

    /**
     * url
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 参数
     */
    private String param;

    /**
     * body参数
     */
    private String body;

    /**
     * token
     */
    private String token;

    /**
     * 请求类型
     */
    private String type;

    /**
     * 备注
     */
    private String note;


}
