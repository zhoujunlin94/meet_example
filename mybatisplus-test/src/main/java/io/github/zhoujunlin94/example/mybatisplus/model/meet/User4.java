package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhoujunlin
 * @date 2023年05月05日 15:58
 * @desc
 */
@Setter
@Getter
@ToString
@TableName("user4")
public class User4 {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "company_id", property = "company.id")
    private Company company;
    private String name;
    private Integer age;
    private String email;

}
