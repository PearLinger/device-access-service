package com.elegoo.cloud.device.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.cloud.device.entity.TTbmqRequestInfo;
import com.elegoo.cloud.device.dao.TTbmqRequestInfoMapper;
import com.elegoo.cloud.device.service.TTbmqRequestInfoService;
import com.elegoo.cloud.device.api.dto.TTbmqRequestInfoDTO;
import com.elegoo.cloud.device.api.vo.TTbmqRequestInfoVO;
import com.elegoo.cloud.device.api.query.TTbmqRequestInfoQuery;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elegoo.cloud.device.tbmq.response.TbmqApiResponse;
import com.elegoo.cloud.device.utils.OkHttpPoolUtil;
import com.elegoo.framework.common.pojo.CommonResult;
import com.elegoo.framework.thirdparty.tbmq.dto.CredentialDTO;
import com.elegoo.framework.thirdparty.tbmq.dto.MqttCredentialsDTO;
import com.elegoo.framework.thirdparty.tbmq.dto.MqttCredentialsDTO.AuthRules;
import com.elegoo.framework.thirdparty.tbmq.dto.MqttCredentialsDTO.CredentialsValue;
import com.elegoo.framework.thirdparty.tbmq.service.CredentialsService;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.elegoo.framework.common.pojo.PageResult;
import com.elegoo.cloud.device.service.TTbmqRequestInfoConvert;
import com.elegoo.framework.common.exception.ServiceException;
import com.elegoo.framework.common.exception.enums.GlobalErrorCodeConstants;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangyi
 * @since 2025-07-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TTbmqRequestInfoServiceImpl extends ServiceImpl<TTbmqRequestInfoMapper, TTbmqRequestInfo> implements TTbmqRequestInfoService {
  @Autowired
  private TTbmqRequestInfoConvert convert;

  @Autowired
  private CredentialsService credentialsService;


	@Override
    public PageResult<TTbmqRequestInfoVO> queryPage(TTbmqRequestInfoQuery pageDTO) {
        Page<TTbmqRequestInfo> page = new Page<TTbmqRequestInfo>(pageDTO.getPageNo(),pageDTO.getPageSize());
        LambdaQueryWrapper<TTbmqRequestInfo> lqw = getWrapper(pageDTO);
        page = this.page(page,lqw);
        return convert.pageEntityConvertToVO(page);
    }

    @Override
    public List<TTbmqRequestInfoVO> queryList(TTbmqRequestInfoQuery query) {
        LambdaQueryWrapper<TTbmqRequestInfo> lqw = getWrapper(query);
        List<TTbmqRequestInfo> list = this.list(lqw);
        return convert.entitysConvertToVO(list);
    }


    @Override
    public TTbmqRequestInfoVO getById(Long id){
        if (id == null){
          throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        TTbmqRequestInfo record = super.getById(id);
        return convert.entityConvertToVO(record);
    }

    @Override
    public Boolean add(TTbmqRequestInfoDTO dto){
        valParams(dto);
        TTbmqRequestInfo record = convert.dtoConvertToEntity(dto);
        return this.save(record);
    }

    @Override
    public Boolean updateById(TTbmqRequestInfoDTO dto){
        if(ObjectUtil.isEmpty(dto.getId())){
           throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        valParams(dto);
        TTbmqRequestInfo record = convert.dtoConvertToEntity(dto);
        return this.updateById(record);
    }

    private void valParams(TTbmqRequestInfoDTO dto){
    }

    @Override
    public Boolean removeById(Long id){
        if (id == null){
        throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        return super.removeById(id);
    }

  @Override
  public CommonResult<CredentialDTO> credentials(MqttCredentialsDTO dto) {
    MqttCredentialsDTO credentials = new MqttCredentialsDTO();
    CredentialsValue credentialsValue = new CredentialsValue();
    credentialsValue.setClientId("productId-deviceId444");
    credentialsValue.setPassword("productId-deviceId");
    credentialsValue.setUserName("productId-deviceId");
    credentials.setCredentialsValueObj(credentialsValue);
    CommonResult<CredentialDTO> credentials1 = credentialsService.credentials(credentials);
    return credentials1;
  }

  @Override
  public Object sendTbmq(TTbmqRequestInfo tTbmqRequestInfo) {
    return null;
  }

  private LambdaQueryWrapper<TTbmqRequestInfo> getWrapper(TTbmqRequestInfoQuery query) {
        LambdaQueryWrapper<TTbmqRequestInfo> lqw = new LambdaQueryWrapper<>();
        if (query != null) {
            if(query.getId() != null){
                lqw.eq(TTbmqRequestInfo::getId, query.getId());
            }
            if(StrUtil.isNotEmpty(query.getUrl())){
                lqw.eq(TTbmqRequestInfo::getUrl, query.getUrl());
            }
            if(StrUtil.isNotEmpty(query.getRequestMethod())){
                lqw.eq(TTbmqRequestInfo::getRequestMethod, query.getRequestMethod());
            }
            if(StrUtil.isNotEmpty(query.getParam())){
                lqw.eq(TTbmqRequestInfo::getParam, query.getParam());
            }
            if(StrUtil.isNotEmpty(query.getBody())){
                lqw.eq(TTbmqRequestInfo::getBody, query.getBody());
            }
            if(StrUtil.isNotEmpty(query.getToken())){
                lqw.eq(TTbmqRequestInfo::getToken, query.getToken());
            }
            if(StrUtil.isNotEmpty(query.getType())){
                lqw.eq(TTbmqRequestInfo::getType, query.getType());
            }
            if(StrUtil.isNotEmpty(query.getNote())){
                lqw.eq(TTbmqRequestInfo::getNote, query.getNote());
            }
        }
        lqw.orderByDesc(TTbmqRequestInfo::getId);
        return lqw;
    }
}
