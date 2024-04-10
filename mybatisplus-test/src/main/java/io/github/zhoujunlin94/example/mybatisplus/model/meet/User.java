package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户实体对应表 user
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long roleId;

    @TableField(exist = false)
    private String ignoreColumn = "ignoreColumn";

    @TableField(exist = false)
    private Integer count;
}
