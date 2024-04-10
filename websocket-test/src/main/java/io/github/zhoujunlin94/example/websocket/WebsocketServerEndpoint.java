package io.github.zhoujunlin94.example.websocket;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.github.zhoujunlin94.example.websocket.handler.PingHandler;
import io.github.zhoujunlin94.example.websocket.handler.base.SocketMessageRouter;
import io.github.zhoujunlin94.example.websocket.message.AuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @author zhoujunlin
 * @desc @ServerEndpoint注解的类不能依赖注入spring bean
 */
@Slf4j
@Component
@ServerEndpoint("/")
public class WebsocketServerEndpoint {

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        // 解析入参token
        String token = CollUtil.getFirst(session.getRequestParameterMap().get(AuthRequest.TOKEN_KEY));
        log.info("[onOpen][session({})接入],携带token:{}", session, token);
        // 创建 AuthRequest 消息类型
        AuthRequest authRequest = new AuthRequest().setToken(token);
        SocketMessageRouter.execute(session, authRequest);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("[onMessage]接收到来自[session({})的一条消息({})]", session, message);
        try {
            if (StrUtil.equalsIgnoreCase(message, PingHandler.PING)) {
                // 心跳
                SocketMessageRouter.pong(session);
                return;
            }
            SocketMessageRouter.execute(session, message);
        } catch (Throwable throwable) {
            log.error("[onMessage][session({})message({}) 发生异常]", session, message, throwable);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.info("[onClose][session({})连接关闭。关闭原因是({})}]", session, closeReason);
        SocketMessageRouter.close(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("[onError][session({}) 发生异常]", session, throwable);
    }

}
