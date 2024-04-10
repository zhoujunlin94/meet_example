package io.github.zhoujunlin94.example.mybatisplus.model.meet;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.*;
import io.github.zhoujunlin94.example.mybatisplus.config.JsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 必须开启autoResultMap = true  否则typeHandler无法使用
@TableName(value = "json_table", autoResultMap = true)
public class JsonTable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * json类型字符串
     */
    @TableField(value = "json_str", typeHandler = JsonTypeHandler.class, fill = FieldFill.INSERT_UPDATE)
    private JSON jsonStr;

    /**
     * json类型
     */
    @TableField(value = "json_obj", typeHandler = JsonTypeHandler.class, fill = FieldFill.INSERT_UPDATE)
    private JSON jsonObj;

}