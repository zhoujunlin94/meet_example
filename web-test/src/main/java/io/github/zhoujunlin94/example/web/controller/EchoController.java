package io.github.zhoujunlin94.example.web.controller;

import io.github.zhoujunlin94.example.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年02月19日 20:27
 * @desc
 */
@Slf4j
@RestController
@RequestMapping("/echo")
public class EchoController {

    @GetMapping("/{msg}")
    public String echoName(@PathVariable String msg) {
        return msg;
    }

    @GetMapping
    public String echoParam(@RequestParam String msg) {
        return msg;
    }

    @PostMapping
    public Map<String, String> echoJson(@RequestBody Map<String, String> msgMap) {
        return msgMap;
    }

    @GetMapping("/user")
    public UserVO echoUser() {
        //return new UserVO().setAge(0).setName("zhoujl");
        //return new UserVO().setAge(null).setName("zhoujl");
        return new UserVO().setAge(30).setName("zhoujl");
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sseEndpoint() {
        return Flux.interval(Duration.ofSeconds(1)) // 每秒发送一个数据
                .take(10)
                .map(sequence -> "SSE Event " + sequence);
    }

}
