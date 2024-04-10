package io.github.zhoujunlin94.example.security.service;

import io.github.zhoujunlin94.example.security.pojo.MeetSecurityUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhoujunlin
 * @date 2023年03月30日 23:16
 * @desc
 */
@Service("userDetailsService")
public class MeetSecurityUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库获取用户信息(密码等等) + 用户角色

        MeetSecurityUser meetSecurityUser = new MeetSecurityUser();
        meetSecurityUser.setUsername(username);
        meetSecurityUser.setPassword(new BCryptPasswordEncoder().encode("123"));

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        meetSecurityUser.setAuthorities(authorities);
        return meetSecurityUser;
    }

}
