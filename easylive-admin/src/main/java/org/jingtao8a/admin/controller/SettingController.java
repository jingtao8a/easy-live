package org.jingtao8a.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.dto.SysSettingDto;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.annotation.Resource;

@Validated
@Slf4j
@RestController
@RequestMapping("/setting")
public class SettingController extends ABaseController {
    @Resource
    private RedisComponent redisComponent;

    @RequestMapping("/getSetting")
    public ResponseVO getSetting(){
        return getSuccessResponseVO(redisComponent.getSysSettingDto());
    }

    @RequestMapping("/saveSetting")
    public ResponseVO saveSetting(SysSettingDto sysSettingDto) {
        redisComponent.saveSysSettingDto(sysSettingDto);
        return getSuccessResponseVO(sysSettingDto);
    }
}