package io.github.zhoujunlin94.example.mybatis.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author zhoujunlin
 * @date 2024/4/7
 * @desc
 */
@Getter
@RequiredArgsConstructor
public enum Sex {

    MAN("男"),
    WOMAN("女"),
    ;

    private final String desc;

}
