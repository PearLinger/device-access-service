package com.elegoo.cloud.device.api.query;

import lombok.Data;
import java.util.Date;
import com.elegoo.framework.common.pojo.PageParam;
/**
* <p>
* 
* </p>
*
* @author yangyi
* @since 2025-07-19
*/
@Data
public class TTbmqRequestInfoQuery extends PageParam{

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
