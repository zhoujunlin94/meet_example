package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Null1 {

    private Long id;

    private String name;

    @TableLogic(delval = "null", value = "1")
    private Integer deleted;
}
