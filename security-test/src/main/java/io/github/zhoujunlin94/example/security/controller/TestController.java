package io.github.zhoujunlin94.example.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2023年03月30日 22:47
 * @desc
 */
@RestController
public class TestController {

    @GetMapping(value = {"", "welcome"})
    public String welcome() {
        return "Welcome!!!";
    }

    @GetMapping("index")
    public String index() {
        return "index!!!";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin!!!";
    }

    @GetMapping("user")
    public String user() {
        return "user!!!";
    }

    @GetMapping("customer")
    public String customer() {
        return "customer!!!";
    }

    @GetMapping("roleAdmin")
    @Secured("ROLE_ADMIN")
    public String roleAdmin() {
        return "roleAdmin!!!";
    }


    @GetMapping("preAuthorize")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String preAuthorize() {
        System.out.println("preAuthorize…………");
        return "preAuthorize!!!";
    }

    @GetMapping("postAuthorize")
    @PostAuthorize("hasAnyRole('ROLE_USER')")
    public String postAuthorize() {
        System.out.println("postAuthorize…………");
        return "PostAuthorize!!!";
    }
}
