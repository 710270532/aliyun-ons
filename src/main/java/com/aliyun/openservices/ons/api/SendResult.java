package com.aliyun.openservices.ons.api;

import com.alibaba.rocketmq.client.VirtualEnvUtil;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.UtilAll;
import com.alibaba.rocketmq.common.message.MessageQueue;

public class SendResult {

    private SendStatus sendStatus;
    private String messageId;
    private MessageQueue messageQueue;
    private long queueOffset;
    private String transactionId;
    

    public SendResult() {
    }
    
    public SendResult(com.alibaba.rocketmq.client.producer.SendResult sendResult){
    	this.sendStatus = sendResult.getSendStatus();
        this.messageId = sendResult.getMsgId();
        this.messageQueue = sendResult.getMessageQueue();
        this.queueOffset = sendResult.getQueueOffset();
        this.transactionId = sendResult.getTransactionId();
    }
    
    public SendResult(SendStatus sendStatus, String msgId, MessageQueue messageQueue, long queueOffset,
            String projectGroupPrefix) {
        this.sendStatus = sendStatus;
        this.messageId = msgId;
        this.messageQueue = messageQueue;
        this.queueOffset = queueOffset;
        if (!UtilAll.isBlank(projectGroupPrefix)) {
            this.messageQueue.setTopic(VirtualEnvUtil.clearProjectGroup(this.messageQueue.getTopic(),
                projectGroupPrefix));
        }
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public SendStatus getSendStatus() {
        return sendStatus;
    }


    public void setSendStatus(SendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }


    public MessageQueue getMessageQueue() {
        return messageQueue;
    }


    public void setMessageQueue(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }


    public long getQueueOffset() {
        return queueOffset;
    }


    public void setQueueOffset(long queueOffset) {
        this.queueOffset = queueOffset;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "SendResult [sendStatus=" + sendStatus + ", messageId=" + messageId + ", messageQueue=" + messageQueue
                + ", queueOffset=" + queueOffset + "]";
    }
}
