package io.github.zhoujunlin94.example.web;

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
 * @date 2023年05月25日 14:41
 * @desc
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = WebTestApplication.class)
public class RetryTest {

    @Resource
    private RetryBean retryBean;

    @Test
    public void retryTest1() throws InterruptedException {
        retryBean.retryMethod();

        new CountDownLatch(1).await();

    }


}
