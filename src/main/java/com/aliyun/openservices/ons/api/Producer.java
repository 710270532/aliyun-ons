package com.aliyun.openservices.ons.api;

public interface Producer{

    public void start();


    public void shutdown();


    public SendResult send(final Message message);


    public void sendOneway(final Message message);
}
