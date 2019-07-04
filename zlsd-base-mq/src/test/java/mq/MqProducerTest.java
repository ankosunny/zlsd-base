package mq;

import com.zhilingsd.base.mq.rocketmq.producer.RocketMqProducer;
import org.apache.rocketmq.client.producer.SendResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MqProducerTest {
    public static void main(String[] args) {

        String namesrvAddr = "127.0.0.1:9876";
        String producerGroupName = "test-lmh";
        Integer sendMsgTimeout = 1000;
        Integer retryTimes = 1;
        RocketMqProducer producer = new RocketMqProducer(namesrvAddr,producerGroupName,sendMsgTimeout,retryTimes);

        try {
            producer.start();

            Date now = new Date();

            SendResult result = producer.synSend("topic1","1-1","测试消息");
            SendResult result1 = producer.synSend("topic2","1-1","测试消息222222222");
            System.out.println(
                    "id:" + result.getMsgId() + " result:" + result.getSendStatus() + " sendtime:"
                            + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(now));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}
