package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhoujunlin94.example.mybatisplus.enums.*;
import lombok.Data;

/**
 * @author zhoujunlin
 * @date 2023年05月06日 15:57
 * @desc
 */
@Data
@TableName("user6")
public class User6 {

    private Long id;

    private String name;

    private String email;

    /**
     * IEnum接口的枚举处理
     */
    private AgeEnum age;

    /**
     * 原生枚举： 默认使用枚举值顺序： 0：MALE， 1：FEMALE
     */
    private GenderEnum gender;

    /**
     * 原生枚举（带{@link com.baomidou.mybatisplus.annotation.EnumValue}):
     * 数据库的值对应该注解对应的属性
     */
    private GradeEnum grade;

    private UserState userState;

    private StrEnum strEnum;

}
