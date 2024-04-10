package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生实体
 */
@Data
@TableName(value = "student")
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Long id;

    private String name;

    private Integer age;

}
