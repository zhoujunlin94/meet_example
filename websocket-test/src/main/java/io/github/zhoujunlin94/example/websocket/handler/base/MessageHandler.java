package io.github.zhoujunlin94.example.websocket.handler.base;

import io.github.zhoujunlin94.example.websocket.message.base.SocketRequest;

import javax.websocket.Session;

/**
 * 消息处理器接口
 *
 * @author zhoujunlin
 */
public interface MessageHandler<T extends SocketRequest> {

    /**
     * 执行处理消息
     *
     * @param session 会话
     * @param message 消息
     */
    void execute(Session session, T message);

    /**
     * @return 消息类型，即每个 Request Message实现类上的 TYPE 静态字段
     */
    String getType();

}
