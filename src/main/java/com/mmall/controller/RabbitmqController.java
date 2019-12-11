package com.mmall.controller;

import com.mmall.rabbitmq.MessageProducer;
import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/rabbitmq")
public class RabbitmqController {
    @Resource
    private MessageProducer messageProducer;
    @RequestMapping(value="/rabbitmqSend",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> rabbitmqSend(HttpServletRequest request, HttpServletResponse response,User user, String name){
        String message = name;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("info", message);
        messageProducer.send(message);
        return map;
    }
}