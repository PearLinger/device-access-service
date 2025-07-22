package com.elegoo.cloud.device.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.cloud.device.entity.TTbmqRequestInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.elegoo.cloud.device.api.dto.TTbmqRequestInfoDTO;
import com.elegoo.cloud.device.api.vo.TTbmqRequestInfoVO;
import com.elegoo.cloud.device.api.query.TTbmqRequestInfoQuery;
import com.elegoo.framework.common.pojo.CommonResult;
import com.elegoo.framework.thirdparty.tbmq.dto.CredentialDTO;
import com.elegoo.framework.thirdparty.tbmq.dto.MqttCredentialsDTO;
import java.util.List;
import com.elegoo.framework.common.pojo.PageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangyi
 * @since 2025-07-19
 */
public interface TTbmqRequestInfoService extends IService<TTbmqRequestInfo> {

    PageResult<TTbmqRequestInfoVO> queryPage(TTbmqRequestInfoQuery pageDTO);

    List<TTbmqRequestInfoVO> queryList(TTbmqRequestInfoQuery query);

    TTbmqRequestInfoVO getById(Long id);

    Boolean add(TTbmqRequestInfoDTO dto);

    Boolean updateById(TTbmqRequestInfoDTO dto);

    Boolean removeById(Long id);

    CommonResult<CredentialDTO> credentials(MqttCredentialsDTO dto);

    Object sendTbmq(TTbmqRequestInfo tTbmqRequestInfo);
}
