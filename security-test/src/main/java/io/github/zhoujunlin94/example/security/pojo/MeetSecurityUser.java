package io.github.zhoujunlin94.example.security.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Set;

/**
 * @author zhoujunlin
 * @date 2023年03月30日 22:57
 * @desc
 */
@Data
@NoArgsConstructor
public class MeetSecurityUser implements UserDetails, Serializable {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态:NORMAL正常  PROHIBIT禁用
     */
    private String status;
    /**
     * 用户角色
     */
    private Set<? extends GrantedAuthority> authorities;
    /**
     * 账户是否过期
     */
    private boolean isAccountNonExpired = true;
    /**
     * 账户是否被锁定
     */
    private boolean isAccountNonLocked = true;
    /**
     * 证书是否过期
     */
    private boolean isCredentialsNonExpired = true;
    /**
     * 账户是否有效
     */
    private boolean isEnabled = true;

}
