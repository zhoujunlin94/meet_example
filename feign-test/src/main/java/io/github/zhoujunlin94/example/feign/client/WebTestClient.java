package io.github.zhoujunlin94.example.feign.client;

import io.github.zhoujunlin94.meet.common.pojo.JsonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年03月28日 22:30
 * @desc
 */
@FeignClient(name = "web-test", url = "http://127.0.0.1:8085")
public interface WebTestClient {

    @PostMapping("/echo")
    JsonResponse echoJson(@RequestBody Map<String, String> msgMap);

}
