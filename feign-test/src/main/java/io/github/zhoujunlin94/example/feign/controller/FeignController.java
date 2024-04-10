package io.github.zhoujunlin94.example.feign.controller;

import io.github.zhoujunlin94.example.feign.client.WebTestClient;
import io.github.zhoujunlin94.meet.common.pojo.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年03月28日 22:33
 * @desc
 */
@RestController
public class FeignController {

    @Resource
    private WebTestClient webTestClient;

    @GetMapping("/echoJson")
    public JsonResponse echoJson() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "zzzzzzzz");
        map.put("age", "50");
        return webTestClient.echoJson(map);
    }

}
