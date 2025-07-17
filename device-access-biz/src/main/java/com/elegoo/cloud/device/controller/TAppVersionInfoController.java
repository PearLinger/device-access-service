package com.elegoo.cloud.device.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.framework.common.pojo.CommonResult;
import com.elegoo.cloud.device.service.TAppVersionInfoService;
import com.elegoo.cloud.device.api.TAppVersionInfoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.elegoo.cloud.device.api.dto.TAppVersionInfoDTO;
import com.elegoo.cloud.device.api.vo.TAppVersionInfoVO;
import com.elegoo.cloud.device.api.query.TAppVersionInfoQuery;


/**
* <p>
*  前端控制器
* </p>
*
* @author yangyi
* @since 2025-07-17
*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/agora-version-info/tapp/version/info")
public class TAppVersionInfoController {

    private final TAppVersionInfoFacade tAppVersionInfoFacade;

    /**
     * 查询分页数据
     */
    @PostMapping(value = "/queryPage")
    public CommonResult<PageResult<TAppVersionInfoVO>> queryPage(@RequestBody TAppVersionInfoQuery pageDTO) {
  PageResult<TAppVersionInfoVO> page = tAppVersionInfoFacade.queryPage(pageDTO);
        return CommonResult.success(page);
    }

    /**
     * 根据tAppVersionInfoId查询
     */
    @PostMapping(value = "/getById")
    public CommonResult<TAppVersionInfoVO> getById(@RequestParam String id) {
        TAppVersionInfoVO result = tAppVersionInfoFacade.getById(id);
        return CommonResult.success(result);
    }

    /**
     * 新增
     */
    @PostMapping(value = "/add")
    public CommonResult<Boolean> add(@RequestBody TAppVersionInfoDTO dto) {
        return CommonResult.success(tAppVersionInfoFacade.add(dto));
    }

    /**
     * 修改
     */
    @PostMapping(value = "/updateById")
    public CommonResult<Boolean> updateById(@RequestBody TAppVersionInfoDTO dto) {
        return CommonResult.success(tAppVersionInfoFacade.updateById(dto));
    }

    /**
     * 删除
     */
    @PostMapping(value = "/removeById")
    public CommonResult<Boolean> removeById(@RequestParam String id) {
        return CommonResult.success(tAppVersionInfoFacade.removeById(id));
    }


}
