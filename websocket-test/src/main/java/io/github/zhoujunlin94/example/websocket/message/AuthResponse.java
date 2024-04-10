package io.github.zhoujunlin94.example.websocket.message;

import io.github.zhoujunlin94.example.websocket.message.base.SocketResponse;
import io.github.zhoujunlin94.example.websocket.message.constant.MessageTypeEnum;
import lombok.Data;

/**
 * 用户认证响应
 */
@Data
public class AuthResponse extends SocketResponse {

    public static final String TYPE = MessageTypeEnum.TO_ONE.name();

    private String bizType = MessageTypeEnum.AUTH_RESPONSE.name();

}
