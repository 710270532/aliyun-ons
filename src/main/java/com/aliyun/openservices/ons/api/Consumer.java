package com.aliyun.openservices.ons.api;

/**
 *
 * <p>Title: consumer interface</p>
 * <p>Description: None</p>
 * @author zhanghongwei
 * @version 2016-12-16 15:31:20
 * @since 1.7
 */
public interface Consumer {
	
	public void start();


    public void shutdown();


    public void subscribe(final String topic, final String subExpression, final MessageListener listener);
    
	
    public void unsubscribe(final String topic);
}
