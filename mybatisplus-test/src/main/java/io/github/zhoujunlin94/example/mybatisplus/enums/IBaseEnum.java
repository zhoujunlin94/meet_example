package io.github.zhoujunlin94.example.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

public interface IBaseEnum<T extends Serializable> extends IEnum<T> {

    String getDescription();
}
