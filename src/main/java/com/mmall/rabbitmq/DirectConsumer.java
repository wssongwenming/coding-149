package com.mmall.rabbitmq;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;
@Service
public class DirectConsumer implements MessageListener {


    public DirectConsumer(){

    }
    @Override
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        try {
            System.out.print("===endDat===="+ new String(message.getBody(),"UTF-8"));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}