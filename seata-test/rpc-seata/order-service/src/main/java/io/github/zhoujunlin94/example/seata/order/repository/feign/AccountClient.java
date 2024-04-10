package io.github.zhoujunlin94.example.seata.order.repository.feign;

import io.github.zhoujunlin94.meet.common.pojo.JsonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年09月23日 11:36
 * @desc
 */
@FeignClient(name = "account", url = "http://127.0.0.1:8891/account")
public interface AccountClient {

    @PostMapping("/reduceBalance")
    JsonResponse reduceBalance(@RequestBody Map<String, Object> requestJson);

}
