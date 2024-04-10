package io.github.zhoujunlin94.example.security.web;

import io.github.zhoujunlin94.meet.common.exception.CommonErrorCode;
import io.github.zhoujunlin94.meet.common.pojo.JsonResponse;
import io.github.zhoujunlin94.meet.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhoujunlin
 * @date 2023年04月04日 13:29
 * @desc
 */
@Slf4j
@Component
public class MeetAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("MeetAccessDeniedHandler");
        JsonResponse unAuthorizationResp = JsonResponse.fail(CommonErrorCode.B_UN_AUTHORIZATION);
        ServletUtils.writeJSON(response, unAuthorizationResp);
    }
}
