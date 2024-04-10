package io.github.zhoujunlin94.example.websocket.handler;

import cn.hutool.core.util.StrUtil;
import io.github.zhoujunlin94.example.websocket.handler.base.MessageHandler;
import io.github.zhoujunlin94.example.websocket.message.AuthRequest;
import io.github.zhoujunlin94.example.websocket.message.AuthResponse;
import io.github.zhoujunlin94.example.websocket.message.UserOnLineNotice;
import io.github.zhoujunlin94.example.websocket.message.constant.SocketErrorCode;
import io.github.zhoujunlin94.example.websocket.util.WebSocketUtil;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

/**
 * @author zhoujunlin
 */
@Component
public class AuthRequestHandler implements MessageHandler<AuthRequest> {

    @Override
    public void execute(Session session, AuthRequest message) {
        // 如果未传递 token
        if (StrUtil.isBlank(message.getToken())) {
            WebSocketUtil.sendTextMessage(session, WebSocketUtil.buildTextMessage(AuthResponse.TYPE,
                    new AuthResponse().error(SocketErrorCode.ACCESS_TOKEN_BLANK)));
            return;
        }

        // 添加到 WebSocketUtil 中
        // todo 考虑到代码简化，我们先直接使用 token 作为 fromUser
        WebSocketUtil.addSession(session, message.getToken());

        // todo 判断是否认证成功。这里，先直接成功
        WebSocketUtil.send(session, AuthResponse.TYPE, new AuthResponse().setMessage("认证成功"));

        // 广播
        // 通知所有人，某个人加入了。
        // 考虑到代码简化，我们先直接使用 token 作为 User
        WebSocketUtil.broadcast(UserOnLineNotice.TYPE,
                new UserOnLineNotice().setFromUser(message.getToken()));
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }

}
