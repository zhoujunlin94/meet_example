package io.github.zhoujunlin94.example.websocket.message.base;

import io.github.zhoujunlin94.example.websocket.message.constant.SocketErrorCode;
import lombok.Data;

/**
 * @author zhoujunlin
 * @date 2023年08月09日 21:47
 * @desc
 */
@Data
public class SocketResponse extends SocketMessage {

    public static final String MESSAGE_TYPE = "responseType";
    public static final String BODY = "body";

    /**
     * 响应状态码
     */
    private Integer code = 0;

    /**
     * 响应提示
     */
    private String message;

    public SocketResponse error(SocketErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
        return this;
    }

}
