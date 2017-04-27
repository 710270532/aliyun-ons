package com.aliyun.openservices.ons.api;

import java.io.Serializable;

import com.alibaba.rocketmq.common.message.MessageExt;

/**
 *
 * <p>Title: Message</p>
 * <p>Description: package rocketmq Message</p>
 * @author zhanghongwei
 * @version 2016-12-16 15:31:20
 * @since 1.7
 * @see com.alibaba.rocketmq.common.message.Message
 */
public class Message extends com.alibaba.rocketmq.common.message.Message implements Serializable{
	
    private static final long serialVersionUID = 1890293425525927118L;
    
    private String msgID;

    public Message() {}

    public Message(MessageExt msg) {
        super(msg.getTopic(), msg.getTags(), msg.getKeys(), msg.getFlag(), msg.getBody(), true);
        setMsgID(msg.getMsgId());
    }

    public Message(String topic, byte[] body) {
        super(topic, "", "", 0, body, true);
    }

    public Message(String topic, String tags, byte[] body) {
    	super(topic, tags, "", 0, body, true);
    }


    public Message(String topic, String tags, String keys, byte[] body) {
        super(topic, tags, keys, 0, body, true);
    }
    
    public Message(String topic, String tags, String keys, int flag, byte[] body, boolean waitStoreMsgOK) {
    	super(topic, tags, keys, flag, body, waitStoreMsgOK);
    }
    
    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgId) {
        this.msgID = msgId;
    }
    
}
