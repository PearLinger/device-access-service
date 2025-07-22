package com.elegoo.cloud.device.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.framework.common.pojo.CommonResult;
import com.elegoo.cloud.device.service.TTbmqRequestInfoService;
import com.elegoo.framework.thirdparty.tbmq.dto.CredentialDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.elegoo.framework.common.pojo.PageResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import com.elegoo.cloud.device.api.dto.TTbmqRequestInfoDTO;
import com.elegoo.cloud.device.api.vo.TTbmqRequestInfoVO;
import com.elegoo.cloud.device.api.query.TTbmqRequestInfoQuery;


/**
* <p>
*  前端控制器
* </p>
*
* @author yangyi
* @since 2025-07-19
*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tbmq-request-info/ttbmq/request/info")
public class TTbmqRequestInfoController {

    private final TTbmqRequestInfoService tTbmqRequestInfoService;

    /**
     * 查询分页数据
     */
    @PostMapping(value = "/queryPage")
    public CommonResult<PageResult<TTbmqRequestInfoVO>> queryPage(@RequestBody TTbmqRequestInfoQuery pageDTO) {
        PageResult<TTbmqRequestInfoVO> page = tTbmqRequestInfoService.queryPage(pageDTO);
        return CommonResult.success(page);
    }

    /**
     * 根据tTbmqRequestInfoId查询
     */
    @PostMapping(value = "/getById")
    public CommonResult<TTbmqRequestInfoVO> getById(@RequestParam Long id) {
        TTbmqRequestInfoVO result = tTbmqRequestInfoService.getById(id);
        return CommonResult.success(result);
    }

    /**
     * 新增
     */
    @PostMapping(value = "/add")
    public CommonResult<Boolean> add(@RequestBody TTbmqRequestInfoDTO dto) {
        return CommonResult.success(tTbmqRequestInfoService.add(dto));
    }

    /**
     * 修改
     */
    @PostMapping(value = "/updateById")
    public CommonResult<Boolean> updateById(@RequestBody TTbmqRequestInfoDTO dto) {
        return CommonResult.success(tTbmqRequestInfoService.updateById(dto));
    }

    /**
     * 删除
     */
    @DeleteMapping(value = "/removeById")
    public CommonResult<Boolean> removeById(@RequestParam Long id) {
        return CommonResult.success(tTbmqRequestInfoService.removeById(id));
    }

    /**
     * 修改
     */
    @PostMapping(value = "/credentials")
    public CommonResult<CredentialDTO> credentials() {
        return tTbmqRequestInfoService.credentials(null);
    }

}
