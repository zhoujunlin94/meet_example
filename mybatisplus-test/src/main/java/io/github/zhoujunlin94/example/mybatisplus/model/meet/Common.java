package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Common {

    private Long id;

    private String name;

    @TableLogic
    private Integer deleted;
}
