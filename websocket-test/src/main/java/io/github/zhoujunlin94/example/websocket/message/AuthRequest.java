package io.github.zhoujunlin94.example.websocket.message;

import io.github.zhoujunlin94.example.websocket.message.base.SocketRequest;
import io.github.zhoujunlin94.example.websocket.message.constant.MessageTypeEnum;
import lombok.Data;

/**
 * 用户认证请求
 *
 * @author zhoujl
 */
@Data
public class AuthRequest extends SocketRequest {
    public static final String TYPE = MessageTypeEnum.AUTH_REQUEST.name();
    public static final String TOKEN_KEY = "token";

    /**
     * 认证 Token
     */
    private String token;

    @Override
    public String getType() {
        return TYPE;
    }
}
