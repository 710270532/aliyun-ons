package com.aliyun.openservices.ons.api;

import java.util.List;
import java.util.Properties;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.UtilAll;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.aliyun.openservices.ons.api.exception.OnsClientException;

/**
 *
 * <p>Title: consumer interface</p>
 * <p>Description: None</p>
 * @author zhanghongwei
 * @version 2016-12-16 15:31:20
 * @since 1.7
 * @see Consumer
 */
public class ConsumerImpl implements Consumer {
	
	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
	
	@SuppressWarnings("unused")
	private ConsumerImpl(){}
	
	public ConsumerImpl(Properties properties){
            try {
                if(properties!=null){
                    String namesrv_addr = properties.getProperty(PropertyKeyConst.NAMESRV_ADDR);
                    if(namesrv_addr==null || "".equals(namesrv_addr)){
                        namesrv_addr = properties.getProperty(PropertyKeyConst.AccessKey);
                    }
                    String messageModel = properties.getProperty(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
                    String consumerGroup = properties.getProperty(PropertyKeyConst.ConsumerId);
                    
                    //必要信息检查
                    if(consumerGroup == null) {
                        throw new OnsClientException("\'ConsumerId\' property is null");
                    }
                    if(namesrv_addr == null){
                        throw new OnsClientException("\'NAMESRV_ADDR\' property is null");
                    }
                    
                    consumer = new DefaultMQPushConsumer(consumerGroup);
                    consumer.setNamesrvAddr(namesrv_addr);
                    consumer.setInstanceName(buildIntanceName(namesrv_addr));

                    //广播还是集群
                    if(PropertyValueConst.BROADCASTING.equals(messageModel)){
                        consumer.setMessageModel(MessageModel.BROADCASTING);
                        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
                    }else{
                        consumer.setMessageModel(MessageModel.CLUSTERING);
                        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);//设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费,如果非第一次启动，那么按照上次消费的位置继续消费
                    }
                    
                    //threads
                    if (properties.containsKey(PropertyKeyConst.ConsumeThreadNums)) {
                        int threadNum = Integer.valueOf(properties.get(PropertyKeyConst.ConsumeThreadNums).toString());
                        consumer.setConsumeThreadMin(threadNum);
                        consumer.setConsumeThreadMax(threadNum);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
	}

	@Override
	public void start() {
            try {
                consumer.start();
            } catch (MQClientException e) {
                e.printStackTrace();
            }
	}

	@Override
	public void shutdown() {
            consumer.shutdown();
	}

	@Override
	public void subscribe(String topic, String subExpression, final MessageListener listener) {
            try {
                consumer.subscribe(topic, subExpression);

                MessageListenerConcurrently listenerConcurrently = new MessageListenerConcurrently() {
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {  
                        MessageExt msg = list.get(0);
                        Message message = new Message(msg);
                        Action action = listener.consume(message, new ConsumeContext());
                        ConsumeConcurrentlyStatus status = ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        if(Action.CommitMessage.equals(action)){
                            status = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        return status;
                    }
               };
                consumer.registerMessageListener(listenerConcurrently);
            } catch (MQClientException e) {
                e.printStackTrace();
            }
	}

	@Override
	public void unsubscribe(String topic) {
            consumer.unsubscribe(topic);
	}
        
        private String buildIntanceName(String namesrv_addr) {
            return Integer.toString(UtilAll.getPid()) + "#" + namesrv_addr.hashCode();
        }

}
