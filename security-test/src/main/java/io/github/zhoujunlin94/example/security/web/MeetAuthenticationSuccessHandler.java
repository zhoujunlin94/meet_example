package io.github.zhoujunlin94.example.security.web;

import io.github.zhoujunlin94.example.security.constant.Constant;
import io.github.zhoujunlin94.example.security.pojo.MeetSecurityUser;
import io.github.zhoujunlin94.example.security.util.JwtTokenUtil;
import io.github.zhoujunlin94.meet.common.pojo.JsonResponse;
import io.github.zhoujunlin94.meet.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhoujunlin
 * @date 2023年04月04日 14:17
 * @desc
 */
@Slf4j
@Component
public class MeetAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.warn("MeetAuthenticationSuccessHandler");
        MeetSecurityUser userDetails = (MeetSecurityUser) authentication.getPrincipal();
        String jwtToken = JwtTokenUtil.generateToken(userDetails.getUsername(), 30 * 60);
        response.setHeader(Constant.AUTHENTICATION, jwtToken);
        ServletUtils.writeJSON(response, JsonResponse.ok(jwtToken));
    }

}
