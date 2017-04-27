package com.aliyun.openservices.ons.api;

import java.util.Properties;

public class ONSFactory {
	
	public static Producer createProducer(final Properties properties) {
		ProducerImpl producer = new ProducerImpl(properties);
        return producer;
    }
	
	public static Consumer createConsumer(final Properties properties) {
		ConsumerImpl consumer = new ConsumerImpl(properties);
        return consumer;
    }
	
	
}
