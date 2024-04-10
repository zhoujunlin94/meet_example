package io.github.zhoujunlin94.example.websocket.handler;

import cn.hutool.core.util.StrUtil;
import io.github.zhoujunlin94.example.websocket.handler.base.MessageHandler;
import io.github.zhoujunlin94.example.websocket.message.SendToAllRequest;
import io.github.zhoujunlin94.example.websocket.message.SendToAllResponse;
import io.github.zhoujunlin94.example.websocket.util.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

/**
 * @author zhoujl
 */
@Slf4j
@Component
public class SendToAllHandler implements MessageHandler<SendToAllRequest> {

    @Override
    public void execute(Session session, SendToAllRequest message) {
        String fromUser = WebSocketUtil.getFromUser(session);
        if (StrUtil.isBlank(fromUser)) {
            log.warn("SendToAllHandler.execute fromUser is blank");
            return;
        }

        SendToAllResponse sendResponse = new SendToAllResponse().setMsgId(message.getMsgId()).setContent(message.getContent())
                .setFromUser(fromUser).setBizType(getType());

        // 广播发送
        WebSocketUtil.broadcast(SendToAllResponse.TYPE, sendResponse);
    }

    @Override
    public String getType() {
        return SendToAllRequest.TYPE;
    }

}
