package io.github.zhoujunlin94.example.websocket.message.constant;

import io.github.zhoujunlin94.meet.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoujunlin
 * @date 2023年07月11日 16:49
 */
@Getter
@AllArgsConstructor
public enum SocketErrorCode implements ErrorCode {
    /**
     * websocket错误码
     */
    ACCESS_TOKEN_BLANK(400, "token缺失"),
    ;

    private final int code;
    private final String msg;

}
