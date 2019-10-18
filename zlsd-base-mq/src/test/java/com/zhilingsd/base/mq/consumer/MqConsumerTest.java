package com.zhilingsd.base.mq.consumer;

import com.zhilingsd.base.mq.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class MqConsumerTest {

    @Test
    public void testConsumer() throws InterruptedException {
        Thread.sleep(20000);
    }
}
