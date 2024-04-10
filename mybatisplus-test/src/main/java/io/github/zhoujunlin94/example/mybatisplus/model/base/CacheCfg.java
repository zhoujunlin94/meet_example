package io.github.zhoujunlin94.example.mybatisplus.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "cache_cfg")
public class CacheCfg implements Serializable {
    /**
     * 主键,配置key
     */
    @TableId(value = "`key`", type = IdType.INPUT)
    private String key;

    /**
     * 配置值
     */
    @TableField(value = "`value`", exist = true)
    private String value;

    /**
     * 配置描述
     */
    @TableField(value = "`desc`")
    private String desc;

    private static final long serialVersionUID = 1L;
}