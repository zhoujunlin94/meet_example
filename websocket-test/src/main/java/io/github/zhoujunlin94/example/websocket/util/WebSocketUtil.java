package io.github.zhoujunlin94.example.websocket.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.alibaba.fastjson.JSONObject;
import io.github.zhoujunlin94.example.websocket.handler.base.MessageHandler;
import io.github.zhoujunlin94.example.websocket.message.base.SocketRequest;
import io.github.zhoujunlin94.example.websocket.message.base.SocketResponse;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 工具类，提供客户端连接的管理等功能
 *
 * @author zhoujunlin
 */
@Slf4j
public final class WebSocketUtil {

    // ========== 会话相关 ==========

    /**
     * Session与用户的映射
     */
    private static final Map<Session, String> SESSION_USER_MAP = new ConcurrentHashMap<>(1024);
    /**
     * 用户与Session的映射
     */
    private static final Map<String, Session> USER_SESSION_MAP = new ConcurrentHashMap<>(1024);

    /**
     * 添加 Session。在这个方法中，会添加用户和 Session 之间的映射
     *
     * @param session  Session
     * @param fromUser 用户
     */
    public static void addSession(Session session, String fromUser) {
        // 更新 USER_SESSION_MAP
        USER_SESSION_MAP.put(fromUser, session);
        // 更新 SESSION_USER_MAP
        SESSION_USER_MAP.put(session, fromUser);
    }

    /**
     * 移除 Session。
     *
     * @param session Session
     */
    public static void removeSession(Session session) {
        // 从 SESSION_USER_MAP 中移除
        String fromUser = SESSION_USER_MAP.remove(session);
        // 从 USER_SESSION_MAP 中移除
        if (StrUtil.isNotBlank(fromUser)) {
            USER_SESSION_MAP.remove(fromUser);
        }
    }

    public static String getFromUser(Session session) {
        return SESSION_USER_MAP.get(session);
    }

    // ========== 消息相关 ==========

    /**
     * 广播发送消息给所有在线用户
     *
     * @param type    消息类型
     * @param message 消息体
     * @param <T>     消息类型
     */
    public static <T extends SocketResponse> void broadcast(String type, T message) {
        // 创建消息
        String messageText = buildTextMessage(type, message);
        // 遍历 SESSION_USER_MAP ，进行逐个发送
        // todo 改为rocketmq广播消息
        for (Session session : SESSION_USER_MAP.keySet()) {
            sendTextMessage(session, messageText);
        }
    }

    /**
     * 发送消息给单个用户的 Session
     *
     * @param session Session
     * @param type    消息类型
     * @param message 消息体
     * @param <T>     消息类型
     */
    public static <T extends SocketResponse> void send(Session session, String type, T message) {
        // 创建消息
        String messageText = buildTextMessage(type, message);
        // 遍历给单个 Session ，进行逐个发送
        if (SESSION_USER_MAP.containsKey(session)) {
            sendTextMessage(session, messageText);
        } else {
            //todo 发送到rocketmq广播消息
        }
    }

    /**
     * 发送消息给指定用户
     *
     * @param targetUser 指定用户
     * @param type       消息类型
     * @param message    消息体
     * @param <T>        消息类型
     * @return 发送是否成功
     */
    public static <T extends SocketResponse> boolean send(String targetUser, String type, T message) {
        // 获得用户对应的 Session
        if (USER_SESSION_MAP.containsKey(targetUser)) {
            Session session = USER_SESSION_MAP.get(targetUser);
            // 发送消息
            send(session, type, message);
        } else {
            // todo 发送到RocketMQ广播
            log.info("[send][user({}) 不存在对应的 session]", targetUser);
        }
        return true;
    }

    /**
     * 构建完整的消息
     *
     * @param type    消息类型
     * @param message 消息体
     * @param <T>     消息类型
     * @return 消息
     */
    public static <T extends SocketResponse> String buildTextMessage(String type, T message) {
        JSONObject socketResponse = new JSONObject();
        socketResponse.put(SocketResponse.MESSAGE_TYPE, type);
        socketResponse.put(SocketResponse.BODY, message);
        return socketResponse.toJSONString();
    }

    /**
     * 真正发送消息
     *
     * @param session     Session
     * @param messageText 消息
     */
    public static void sendTextMessage(Session session, String messageText) {
        if (Objects.isNull(session)) {
            log.error("[sendTextMessage][session为null]");
            return;
        }
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (Objects.isNull(basic)) {
            log.error("[sendTextMessage][remote endpoint为null]");
            return;
        }
        try {
            basic.sendText(messageText);
        } catch (IOException e) {
            log.error("[sendTextMessage][session({}) 发送消息{}) 发生异常",
                    session, messageText, e);
        }
    }

    public static Class<SocketRequest> getMessageClass(MessageHandler<SocketRequest> handler) {
        return (Class<SocketRequest>) TypeUtil.getClass(TypeUtil.getTypeArgument(handler.getClass()));
    }

}
