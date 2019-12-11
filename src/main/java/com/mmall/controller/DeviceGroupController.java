package com.mmall.controller;

import com.mmall.service.DisplayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("sys/device")
@Slf4j
public class DeviceGroupController {
    @Resource
    private DisplayService displayService;
    @RequestMapping("group.page")
    public ModelAndView page() {
        return new ModelAndView("devicegroup");
    }
}
