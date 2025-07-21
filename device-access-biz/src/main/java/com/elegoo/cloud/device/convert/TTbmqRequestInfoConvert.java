package com.elegoo.cloud.device.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.elegoo.cloud.device.entity.TTbmqRequestInfo;
import com.elegoo.cloud.device.api.dto.TTbmqRequestInfoDTO;
import com.elegoo.cloud.device.api.vo.TTbmqRequestInfoVO;
import java.util.List;
import com.elegoo.framework.common.pojo.PageResult;

/**
* <p>
  *  服务实现类
  * </p>
*
* @author yangyi
* @since 2025-07-19
*/

@Mapper(componentModel = "spring")
public interface TTbmqRequestInfoConvert {
      @Mapping(source = "records", target = "list")
      PageResult<TTbmqRequestInfoVO> pageEntityConvertToVO(Page<TTbmqRequestInfo> pageInfo);

      TTbmqRequestInfo dtoConvertToEntity(TTbmqRequestInfoDTO dto);

      TTbmqRequestInfoVO entityConvertToVO(TTbmqRequestInfo entity);

      List<TTbmqRequestInfoVO> entitysConvertToVO(List<TTbmqRequestInfo> entitys);
  }
