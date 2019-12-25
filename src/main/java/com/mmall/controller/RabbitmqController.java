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
    @RequestMapping(value="/initcamera",method = RequestMethod.GET)
    @ResponseBody
    public void rabbitmqSendtoCamera(HttpServletRequest request, HttpServletResponse response,String json){

        //messageProducer.sendDirectMessage("camera-to-server-exchange","camera-to-server-markdata",message);
        messageProducer.sendTopicMessage("server-to-other-exchange","server-to-camera-command-routing-key-1",json);

    }

    @RequestMapping(value="/initdisplay",method = RequestMethod.GET)
    @ResponseBody
    public void initDisplay(HttpServletRequest request, HttpServletResponse response,String json){

        messageProducer.sendTopicMessage("server-to-other-exchange","server-to-display-command-routing-key-200",json);

    }

    @RequestMapping(value="/signinbypass",method = RequestMethod.GET)
    @ResponseBody
    public void rabbitmqSignByPass(HttpServletRequest request, HttpServletResponse response){
        String signByPassCommand="{\"code\":0,\"message\":\"登陆者信息\",\"index\":1,\"data\":{\"userId\":1,\"name\":\"张三\",\"department\":\"某某单位\",\"password\":\"111111\",\"shooting_gun\":\"手枪\",\"photopath\":\"\",\"bullet_count\":10,\"target_number\":\"12\",\"group_number\":\"1\"}}";
        //messageProducer.sendDirectMessage("camera-to-server-exchange","camera-to-server-markdata",message);
        messageProducer.sendTopicMessage("server-to-other-exchange","server-to-display-command-routing-key-200",signByPassCommand);

    }

    @RequestMapping(value="/senddata",method = RequestMethod.GET)
    @ResponseBody
    public void rabbitmqSendData(HttpServletRequest request, HttpServletResponse response){
        String markData="{\n" +
                "    \"mac\": \"DC4A3EE6B02F\", \n" +
                "    \"radius\": \"68.64\", \n" +
                "    \"mmOfRadius\": \"50.50\", \n" +
                "    \"holes\": [\n" +
                "        {\n" +
                "       \"id\":1,\n" +
                "       \"offset\":\"l\",\n" +
                "            \"px\": -342.53, \n" +
                "            \"py\": -235.55, \n" +
                "            \"mx\": -252.01, \n" +
                "            \"my\": -173.3, \n" +
                "            \"score\": 4\n" +
                "        }, \n" +
                "        {\n" +
                "      \"id\":2, \n" +
                "       \"offset\":\"r\",\n" +
                "            \"px\": 342.6, \n" +
                "            \"py\": -234.67, \n" +
                "            \"mx\": 252.06, \n" +
                "            \"my\": -172.65, \n" +
                "            \"score\": 4\n" +
                "        }, \n" +
                "        {\n" +
                "   \"id\":3, \n" +
                "       \"offset\":\"ru\",\n" +
                "            \"px\": -264.31, \n" +
                "            \"py\": -225.86, \n" +
                "            \"mx\": -194.46, \n" +
                "            \"my\": -166.17, \n" +
                "            \"score\": 5\n" +
                "        }, \n" +
                "        {\n" +
                "   \"id\":4, \n" +
                "       \"offset\":\"lu\",\n" +
                "            \"px\": 267.31, \n" +
                "            \"py\": -224.02, \n" +
                "            \"mx\": 196.67, \n" +
                "            \"my\": -164.82, \n" +
                "            \"score\": 5\n" +
                "        } \n" +
                "    ]\n" +
                "}";
        //messageProducer.sendDirectMessage("camera-to-server-exchange","camera-to-server-markdata",message);
        messageProducer.sendDirectMessage("camera-to-server-exchange","camera-to-server-markdata",markData);

    }
}