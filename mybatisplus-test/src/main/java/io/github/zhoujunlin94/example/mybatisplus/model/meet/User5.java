package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoujunlin
 * @date 2023年05月05日 17:07
 * @desc
 */
@Data
@TableName(value = "user5")
@NoArgsConstructor
@AllArgsConstructor
public class User5 {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operator;

}
