package io.github.zhoujunlin94.example.web.controller;

import io.github.zhoujunlin94.example.web.event.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Max;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 16:43
 * @desc
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/event")
public class EventController {

    @Resource
    private UserService userService;

    @GetMapping("/register")
    public String register(@RequestParam String userName) {
        userService.register(userName);
        return "success";
    }

    @GetMapping("/valid")
    public String register(@Max(value = 150, message = "age不超过150") @RequestParam Integer age) {
        return age.toString();
    }

}
