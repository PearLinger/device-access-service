package com.elegoo.cloud.device.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;
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
@TableName("t_app_version_info")
public class TAppVersionInfo extends Model<TAppVersionInfo> {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
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
