package com.aliyun.openservices.ons.api;

public interface MessageListener {
	
	public Action consume(final Message message, final ConsumeContext context);
	
}
