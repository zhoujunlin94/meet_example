package io.github.zhoujunlin94.example.websocket.message.base;

/**
 * @author zhoujunlin
 * @date 2023年08月09日 21:55
 * @desc
 */
public abstract class SocketRequest extends SocketMessage {

    public static final String MESSAGE_TYPE = "requestType";
    public static final String BODY = "body";

    public abstract String getType();

}
