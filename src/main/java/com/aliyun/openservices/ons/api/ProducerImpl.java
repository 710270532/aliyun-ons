package com.aliyun.openservices.ons.api;

import java.util.Properties;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.remoting.exception.RemotingException;


public class ProducerImpl implements Producer{
	
    private DefaultMQProducer producer = new DefaultMQProducer();

    @SuppressWarnings("unused")
    private ProducerImpl(){}

    public ProducerImpl(Properties properties){
        try {
            if(properties!=null){
                    String namesrv_addr = properties.getProperty(PropertyKeyConst.NAMESRV_ADDR, "");
                    if(namesrv_addr==null || "".equals(namesrv_addr)){
                        namesrv_addr = properties.getProperty(PropertyKeyConst.AccessKey, "");
                    }
                    String producerGroup = properties.getProperty(PropertyKeyConst.ProducerId, "__ONS_PRODUCER_DEFAULT_GROUP");
                    producer = new DefaultMQProducer(producerGroup);
                    producer.setNamesrvAddr(namesrv_addr);
                    producer.setMaxMessageSize(1024 * 128);
            }
        } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
        }
    }

    @Override
    public void start() {
        try {
                producer.start();
        } catch (MQClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
            producer.shutdown();
    }

    @Override
    public SendResult send(Message message) {
        SendResult sendResult = null;
        try {
                sendResult = new SendResult(producer.send(message));
        } catch (MQClientException e) {
                e.printStackTrace();
        } catch (RemotingException e) {
                e.printStackTrace();
        } catch (MQBrokerException e) {
                e.printStackTrace();
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        return sendResult;
    }

    @Override
    public void sendOneway(Message message) {
        try {
                producer.sendOneway(message);
        } catch (MQClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (RemotingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }

}
