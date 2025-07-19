package com.elegoo.cloud.device.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.cloud.device.entity.TAppVersionInfo;
import com.elegoo.cloud.device.dao.TAppVersionInfoMapper;
import com.elegoo.cloud.device.service.TAppVersionInfoService;
import com.elegoo.cloud.device.api.dto.TAppVersionInfoDTO;
import com.elegoo.cloud.device.api.vo.TAppVersionInfoVO;
import com.elegoo.cloud.device.api.query.TAppVersionInfoQuery;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.elegoo.framework.common.pojo.PageResult;
import com.elegoo.cloud.device.service.TAppVersionInfoConvert;
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
public class TAppVersionInfoServiceImpl extends ServiceImpl<TAppVersionInfoMapper, TAppVersionInfo> implements TAppVersionInfoService {
  @Autowired
  private TAppVersionInfoConvert convert;
	@Override
    public PageResult<TAppVersionInfoVO> queryPage(TAppVersionInfoQuery pageDTO) {
        Page<TAppVersionInfo> page = new Page<TAppVersionInfo>(pageDTO.getPageNo(),pageDTO.getPageSize());
        LambdaQueryWrapper<TAppVersionInfo> lqw = getWrapper(pageDTO);
        page = this.page(page,lqw);
        return convert.pageEntityConvertToVO(page);
    }

    @Override
    public List<TAppVersionInfoVO> queryList(TAppVersionInfoQuery query) {
        LambdaQueryWrapper<TAppVersionInfo> lqw = getWrapper(query);
        List<TAppVersionInfo> list = this.list(lqw);
        return convert.entitysConvertToVO(list);
    }


    @Override
    public TAppVersionInfoVO getById(Long id){
        if (id == null){
          throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        TAppVersionInfo record = super.getById(id);
        return convert.entityConvertToVO(record);
    }

    @Override
    public Boolean add(TAppVersionInfoDTO dto){
        valParams(dto);
        TAppVersionInfo record = convert.dtoConvertToEntity(dto);
        return this.save(record);
    }

    @Override
    public Boolean updateById(TAppVersionInfoDTO dto){
        if(ObjectUtil.isEmpty(dto.getId())){
           throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        valParams(dto);
        TAppVersionInfo record = convert.dtoConvertToEntity(dto);
        return this.updateById(record);
    }

    private void valParams(TAppVersionInfoDTO dto){
    }

    @Override
    public Boolean removeById(Long id){
        if (id == null){
        throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        return super.removeById(id);
    }

    private LambdaQueryWrapper<TAppVersionInfo> getWrapper(TAppVersionInfoQuery query) {
        LambdaQueryWrapper<TAppVersionInfo> lqw = new LambdaQueryWrapper<>();
        if (query != null) {
            if(query.getId() != null){
                lqw.eq(TAppVersionInfo::getId, query.getId());
            }
            if(StrUtil.isNotEmpty(query.getVersion())){
                lqw.eq(TAppVersionInfo::getVersion, query.getVersion());
            }
            if(StrUtil.isNotEmpty(query.getFeatureUrl())){
                lqw.eq(TAppVersionInfo::getFeatureUrl, query.getFeatureUrl());
            }
            if(query.getFlag() != null){
                lqw.eq(TAppVersionInfo::getFlag, query.getFlag());
            }
            if(StrUtil.isNotEmpty(query.getOs())){
                lqw.eq(TAppVersionInfo::getOs, query.getOs());
            }
            if(query.getTime() != null){
                lqw.eq(TAppVersionInfo::getTime, query.getTime());
            }
            if(query.getStartTime() != null){
                lqw.eq(TAppVersionInfo::getStartTime, query.getStartTime());
            }
            if(query.getPublishStatus() != null){
                lqw.eq(TAppVersionInfo::getPublishStatus, query.getPublishStatus());
            }
        }
        lqw.orderByDesc(TAppVersionInfo::getId);
        return lqw;
    }
}
