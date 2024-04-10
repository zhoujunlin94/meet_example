package io.github.zhoujunlin94.example.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import io.github.zhoujunlin94.example.rabbitmq.producer.Demo01Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFutureCallback;

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
public class Demo01ProducerTest {

    @Resource
    private Demo01Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id);
        log.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void tesSyncSendDefault() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSendDefault(id);
        log.info("[tesSyncSendDefault][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testAsyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend(id).addCallback(new ListenableFutureCallback<Void>() {

            @Override
            public void onFailure(Throwable e) {
                log.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }

            @Override
            public void onSuccess(Void aVoid) {
                log.info("[testASyncSend][发送编号：[{}] 发送成功]", id);
            }

        });
        log.info("[testASyncSend][发送编号：[{}] 调用完成]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testAsyncSend2() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend2(id).addCallback(new ListenableFutureCallback<Message>() {
            @Override
            public void onSuccess(Message result) {
                log.info("[testAsyncSend2][发送编号：[{}] 发送成功, result:{}]", id, JSONObject.toJSONString(result));
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("[testAsyncSend2][发送编号：[{}] 发送异常]]", id, ex);
            }
        });
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }


}
