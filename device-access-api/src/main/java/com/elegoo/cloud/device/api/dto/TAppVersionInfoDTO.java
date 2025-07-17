package com.elegoo.cloud.device.api.dto;

import lombok.Data;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangyi
 * @since 2025-07-17
 */
@Data
public class TAppVersionInfoDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 版本号
     */
    private String version;

    /**
     * 功能介绍URL
     */
    private String featureUrl;

    /**
     * 升级标识：0无需升级 1提醒升级 2强制升级
     */
    private Integer flag;

    /**
     * 系统: 1Android 2iOS
     */
    private String os;

    /**
     * 创建时间
     */
    private Date time;

    /**
     * 开始生效时间
     */
    private Date startTime;

    /**
     * 发布状态
     */
    private Integer publishStatus;


}
