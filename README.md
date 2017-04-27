# ons

[java-ons](https://github.com/710270532/ons) is based on [apache rocketmq](https://github.com/apache/incubator-rocketmq) development, the open source api which is to replace [aliyun](https://www.aliyun.com) ons java pai.

##	Install

```xml
<dependency>
    <groupId>hongwei.zhang</groupId>
    <artifactId>ons</artifactId>
    <version>1.0.0</version>
</dependency>
```


##	Function
version1.0.0
1.普通集群消息发放
2.广播消息发放
3.延迟消息队列（需要在rocketmq服务端配置）


##	User Manual
参见src/test/java目录下的ProducerTest与ComsumerTest类


##	About Us
注：因为阿里云ons为收费服务，本项目适用：  
> 1.已经使用阿里云api进行开发后需要迁移自己维护rocketmq的项目。  
> 2.想使用阿里云简洁api的项目。  
> 3.只实现了阿里云ons java api的基础部分功能，更多功能有待于完善。  

version 1.0.0, Any questions, please send mail to <zhanghongwei@le.com>.
