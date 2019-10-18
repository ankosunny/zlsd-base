package com.zhilingsd.base.mq.producer;

import com.zhilingsd.base.mq.TestApplication;
import com.zhilingsd.base.mq.bean.MQTestBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class MqProducerTest {

    @Autowired
    private Producer producer;

    @Test
    public void testProducer() {
        MQTestBean mqTestBean = new MQTestBean();
        mqTestBean.setId(11111L);
        mqTestBean.setName("张三");
        mqTestBean.setSex("男");
        mqTestBean.setMills(System.currentTimeMillis());
        producer.synSend("test_topic", "test_tag", mqTestBean);
    }
}
