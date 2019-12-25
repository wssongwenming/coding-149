package com.mmall.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CameraMarkDataConsumer implements ChannelAwareMessageListener {
    @Resource
    private AmqpTemplate amqpTemplate;
    @Resource
    private MessageProducer messageProducer;
    public CameraMarkDataConsumer(){
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // TODO Auto-generated method stub
        try {
            //String messageJson=new String(message.getBody(),"UTF-8");
            messageProducer.sendTopicMessage("server-to-other-exchange","server-to-display-markdata-routing-key-200",message);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.print("tag========"+ message.getMessageProperties().getDeliveryTag());
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

 /*   @Override
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        try {
            System.out.print("===endDat===="+ new String(message.getBody(),"UTF-8"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}