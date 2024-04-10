package io.github.zhoujunlin94.example.websocket.handler.base;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import io.github.zhoujunlin94.example.websocket.handler.PingHandler;
import io.github.zhoujunlin94.example.websocket.message.base.SocketRequest;
import io.github.zhoujunlin94.example.websocket.util.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023年08月10日 22:18
 * @desc socket消息路由处理器
 */
@Slf4j
@Component
public class SocketMessageRouter implements InitializingBean {
    /**
     * 消息类型与 MessageHandler 的映射
     */
    private static final Map<String, MessageHandler<SocketRequest>> MESSAGE_HANDLERS = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        // 通过 ApplicationContext 获得所有 MessageHandler Bean
        SpringUtil.getBeansOfType(MessageHandler.class).values()
                .forEach(messageHandler -> MESSAGE_HANDLERS.put(messageHandler.getType(), messageHandler));
        log.info("[SocketMessageRouter.afterPropertiesSet][消息处理器数量：{}]", MESSAGE_HANDLERS.size());
    }

    public static void execute(Session session, SocketRequest socketRequest) {
        // 获得消息处理器
        MessageHandler<SocketRequest> messageHandler = MESSAGE_HANDLERS.get(socketRequest.getType());
        if (Objects.isNull(messageHandler)) {
            log.error("[不存在消息处理器]-消息:{}", socketRequest);
            return;
        }
        messageHandler.execute(session, socketRequest);
    }

    public static void execute(Session session, String message) {
        // 获得消息类型
        JSONObject messageJson = JSONObject.parseObject(message);
        String messageType = messageJson.getString(SocketRequest.MESSAGE_TYPE);
        // 获得消息处理器
        MessageHandler<SocketRequest> messageHandler = MESSAGE_HANDLERS.get(messageType);
        if (Objects.isNull(messageHandler)) {
            log.error("[不存在消息处理器]-消息:{}", message);
            return;
        }
        // 解析消息
        Class<SocketRequest> messageClass = WebSocketUtil.getMessageClass(messageHandler);
        SocketRequest socketRequest = JSONObject.parseObject(messageJson.getString(SocketRequest.BODY), messageClass);
        messageHandler.execute(session, socketRequest);
    }

    public static void close(Session session) {
        WebSocketUtil.removeSession(session);
    }

    public static void pong(Session session) {
        SpringUtil.getBean(PingHandler.class).execute(session);
    }

}
