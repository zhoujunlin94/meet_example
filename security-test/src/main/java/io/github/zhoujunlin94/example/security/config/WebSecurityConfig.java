package io.github.zhoujunlin94.example.security.config;

import io.github.zhoujunlin94.example.security.filter.JwtAuthenticationTokenFilter;
import io.github.zhoujunlin94.example.security.service.MeetSecurityUserDetailsService;
import io.github.zhoujunlin94.example.security.web.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年03月30日 22:47
 * @desc
 */
@Configuration
public class WebSecurityConfig {

    @Resource
    MeetAuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    MeetAuthenticationSuccessHandler authenticationSuccessHandler;
    @Resource
    MeetAuthenticationFailureHandler authenticationFailureHandler;
    @Resource
    MeetLogoutSuccessHandler logoutSuccessHandler;
    @Resource
    MeetAccessDeniedHandler accessDeniedHandler;
    @Resource
    MeetSecurityUserDetailsService userDetailsService;
    @Resource
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 去掉 CSRF
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)

                .and()
                .authorizeRequests()
                .anyRequest()
                .access("@rbacauthorityservice.hasPermission(request, authentication)")

                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()

                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();

        httpSecurity.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService).tokenValiditySeconds(300);

        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }
}

