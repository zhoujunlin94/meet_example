package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Null2 {

    private Long id;

    private String name;

    @TableLogic(delval = "now()", value = "null")
    private LocalDateTime delTime;
}
