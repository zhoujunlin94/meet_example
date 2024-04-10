package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("company")
public class Company {
    private Long id;
    private String name;
    List<User4> userList;

}
