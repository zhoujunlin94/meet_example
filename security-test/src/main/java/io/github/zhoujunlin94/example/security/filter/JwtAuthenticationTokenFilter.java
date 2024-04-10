package io.github.zhoujunlin94.example.security.filter;

import cn.hutool.core.util.StrUtil;
import io.github.zhoujunlin94.example.security.constant.Constant;
import io.github.zhoujunlin94.example.security.service.MeetSecurityUserDetailsService;
import io.github.zhoujunlin94.example.security.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023年04月04日 15:06
 * @desc
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private MeetSecurityUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(Constant.AUTHENTICATION);

        if (StrUtil.startWith(authHeader, Constant.TOKEN_PREFIX)) {
            String token = StrUtil.subAfter(authHeader, Constant.TOKEN_PREFIX, Boolean.FALSE);
            String username = JwtTokenUtil.parseSubjectFromToken(token);

            if (StrUtil.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (Objects.nonNull(userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }

        filterChain.doFilter(request, response);
    }
}
