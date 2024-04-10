package io.github.zhoujunlin94.example.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhoujunlin
 * @date 2023年04月04日 15:17
 * @desc
 */
@Component("rbacauthorityservice")
public class RbacAuthorityService {

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        // true 不需要权限控制   false需要登录
        boolean hasPermission = true;
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Set<String> urls = new HashSet<>();
            // 这些 url 都是要登录后才能访问，且其他的 url 都不能访问！  可以配置到数据库哪些需要登录  哪些可以直接访问
            urls.add("/postAuthorize");
            urls.add("/preAuthorize");
            urls.add("/roleAdmin");

            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = false;
                    break;
                }
            }

            return hasPermission;
        }
        return true;
    }

}
