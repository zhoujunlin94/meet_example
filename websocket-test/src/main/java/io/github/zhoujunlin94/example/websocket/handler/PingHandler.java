package io.github.zhoujunlin94.example.websocket.handler;

import io.github.zhoujunlin94.example.websocket.util.WebSocketUtil;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

/**
 * @author zhoujunlin
 * @date 2023年08月09日 22:09
 * @desc
 */
@Component
public class PingHandler {

    public static final String PING = "PING";
    public static final String PONG = "PONG";

    public void execute(Session session) {
        WebSocketUtil.sendTextMessage(session, PONG);
    }

}
