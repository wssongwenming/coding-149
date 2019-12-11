package com.mmall.rabbitmq;


import com.google.gson.Gson;
import entity.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.logging.Logger;

@Service
public class MessageProducer {
    @Resource
    private AmqpTemplate amqpTemplate;

    public void send(Object message) {

        Gson gson = new Gson();
        User user = new User();
        user.setName("ty");
        user.setAge("27");
        amqpTemplate.convertAndSend("com.ty.test", gson.toJson(user));
    }
}