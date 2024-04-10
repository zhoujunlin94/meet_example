package io.github.zhoujunlin94.example.security.web;

import io.github.zhoujunlin94.meet.common.pojo.JsonResponse;
import io.github.zhoujunlin94.meet.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhoujunlin
 * @date 2023年04月04日 14:22
 * @desc
 */
@Slf4j
@Component
public class MeetLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.warn("MeetLogoutSuccessHandler");
        /**
         * 怎么token失效?
         * 生成的时候放到redis
         * 解析的时候需要判断redis中的token与入参token一致  (如果有这种校验的话  RSA加密就没必要了吧)
         * 退出的时候删除redis
         */
        ServletUtils.writeJSON(response, JsonResponse.ok("Logout Success"));
    }
}
