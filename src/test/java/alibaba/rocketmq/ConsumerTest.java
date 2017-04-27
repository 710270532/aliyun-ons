package alibaba.rocketmq;

import java.util.Properties;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;

public class ConsumerTest {
	
    private static Consumer consumer = null;
	
    public static void main(String[] args) {
    	Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "test_goroup");
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, "server0:9876;server1:9876");	//name server地址
        properties.put(PropertyKeyConst.ConsumeThreadNums, "1");                       		//控制线程数
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);		//以广播形式接收消息

	consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("topic", "*", new MessageListener(){
            @Override
            public Action consume(Message message, ConsumeContext context) {
                    System.out.println(new String(message.getBody()));
                    return Action.CommitMessage;
            }
        });
        consumer.start();
    }
}
