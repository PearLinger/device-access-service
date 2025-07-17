package com.elegoo.cloud.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rinoiot.base.exception.BaseException;
import com.rinoiot.base.model.CommonErrorCodes;
import com.elegoo.cloud.device.entity.TAppVersionInfo;
import com.elegoo.cloud.device.dao.TAppVersionInfoMapper;
import com.elegoo.cloud.device.service.TAppVersionInfoService;
import com.elegoo.cloud.device.api.dto.TAppVersionInfoDTO;
import com.elegoo.cloud.device.api.vo.TAppVersionInfoVO;
import com.elegoo.cloud.device.api.query.TAppVersionInfoQuery;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rinoiot.base.model.QueryPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.rinoiot.mybatisplus.utils.PageUtil;
import com.rinoiot.core.utils.BeanUtils;
import cn.hutool.core.util.StrUtil;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangyi
 * @since 2025-07-17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TAppVersionInfoServiceImpl extends ServiceImpl<TAppVersionInfoMapper, TAppVersionInfo> implements TAppVersionInfoService {

	@Override
    public PageResult<TAppVersionInfoVO> queryPage(TAppVersionInfoQuery pageDTO) {
        Page<TAppVersionInfo> page = new Page<TAppVersionInfo>(pageDTO.getPageNo(),pageDTO.getPageSize());
        LambdaQueryWrapper<TAppVersionInfo> lqw = getWrapper(pageDTO.getQueryCondition());

        PageResult<TAppVersionInfoVO> pageResult = new PageResult<>();
        pageResult.setList(toRespVOS(page.getList(), false));
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public List<TAppVersionInfoVO> queryList(TAppVersionInfoQuery query) {
        LambdaQueryWrapper<TAppVersionInfo> lqw = getWrapper(query);
        List<TAppVersionInfo> list = this.list(lqw);
        return BeanUtils.copyListAndOperator(list,TAppVersionInfoVO.class,(source, target) -> setOther(target));
    }

    private void setOther(TAppVersionInfoVO target) {
    }

    @Override
    public TAppVersionInfoVO getById(String id){
        if (StrUtil.isEmpty(id)){
            throw new BaseException(CommonErrorCodes.MISS_PARAMS);
        }
        TAppVersionInfoVO result = null;
        TAppVersionInfo record = super.getById(id);
        if(record != null){
            result = new TAppVersionInfoVO();
            BeanUtils.copyProperties(record,result);
        }
        return result;
    }

    @Override
    public Boolean add(TAppVersionInfoDTO dto){
        valParams(dto);
        TAppVersionInfo record = new TAppVersionInfo();
        BeanUtils.copyProperties(dto,record);
        return this.save(record);
    }

    @Override
    public Boolean updateById(TAppVersionInfoDTO dto){
        if(StrUtil.isBlank(dto.getId())){
            throw new BaseException(CommonErrorCodes.MISS_PARAMS);
        }
        valParams(dto);
        TAppVersionInfo record = new TAppVersionInfo();
        BeanUtils.copyProperties(dto,record);
        return this.updateById(record);
    }

    private void valParams(TAppVersionInfoDTO dto){
    }

    @Override
    public Boolean removeById(String id){
        if (StrUtil.isEmpty(id)){
            throw new BaseException(CommonErrorCodes.MISS_PARAMS);
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
