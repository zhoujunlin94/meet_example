package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import lombok.Data;

@Data
public class Role {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDescribe;
}
