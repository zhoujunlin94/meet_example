package io.github.zhoujunlin94.example.websocket.message.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author zhoujunlin
 * @date 2023年07月11日 16:44
 */
@Getter
@RequiredArgsConstructor
public enum MessageTypeEnum {
    /**
     * 消息类型 请求与响应
     */
    AUTH_REQUEST("认证请求"),
    AUTH_RESPONSE("认证响应"),

    USER_ON_LINE_NOTICE("用户上线通知"),

    SEND_TO_ALL_REQUEST("发送给所有用户请求"),
    SEND_TO_ONE_REQUEST("发送给某个用户请求"),

    SEND_RESPONSE("发送响应"),

    /**
     * 回复类型
     */
    TO_ONE("回复某个人"),
    TO_ALL("回复所有人"),
    ;

    private final String desc;

}
