package alibaba.rocketmq;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;

public class ProducerTest {
    private static Producer producer = null;

    public static void main(String[] args) {
        boolean flag = false;

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, "test_group");		
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, "server0:9876;server1:9876");//name server地址

        producer = ONSFactory.createProducer(properties);
        producer.start();

        try {
            for(int i=0;i<1;i++){
                Message msg = new Message("topic", "key", ("Hello MetaQ" + i).getBytes());// topic, tag, key, body
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(producer!=null){
                producer.shutdown();
            }
        }
        System.out.println(flag);
    }
}
