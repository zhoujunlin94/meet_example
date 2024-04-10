package io.github.zhoujunlin94.example.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.zhoujunlin94.example.web.config.json.ConvertIntegerSerializer;
import lombok.Data;

/**
 * @author zhoujunlin
 * @date 2023年04月21日 21:50
 * @desc
 */
@Data
public class UserVO {

    private String name;

    @JSONField(serializeUsing = ConvertIntegerSerializer.class)
    private Integer age;

}
