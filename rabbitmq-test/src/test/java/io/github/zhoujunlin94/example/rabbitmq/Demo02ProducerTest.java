package io.github.zhoujunlin94.example.rabbitmq;

import io.github.zhoujunlin94.example.rabbitmq.producer.Demo02Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 18:32
 * @desc
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "demo01")
@SpringBootTest(classes = RabbitMQTestApplication.class)
public class Demo02ProducerTest {

    @Resource
    private Demo02Producer producer;

    @Test
    public void testSyncSendSuccess() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        // 匹配到队列  发送、消费成功
        producer.syncSend(id, "da.yu.nai");
        log.info("[testSyncSendSuccess][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testSyncSendFailure() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        // 匹配不到队列  发送成功  无法消费
        producer.syncSend(id, "yu.nai.shuai");
        log.info("[testSyncSendFailure][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }


}
