package io.github.zhoujunlin94.example.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年05月25日 14:38
 * @desc
 */
@Slf4j
@Component
public class RetryBean {

    /**
     * 当抛出RuntimeException时  每隔1秒重试1次  共4次
     * 最后才抛出异常
     */
    @Retryable(maxAttempts = 4, backoff = @Backoff(delay = 2000), value = {RuntimeException.class})
    public void retryMethod() {
        log.warn("进入方法------>");
        throw new RuntimeException("故意抛出的异常");
    }

}
