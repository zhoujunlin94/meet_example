package io.github.zhoujunlin94.example.websocket.handler;

import cn.hutool.core.util.StrUtil;
import io.github.zhoujunlin94.example.websocket.handler.base.MessageHandler;
import io.github.zhoujunlin94.example.websocket.message.SendToOneRequest;
import io.github.zhoujunlin94.example.websocket.message.SendToOneResponse;
import io.github.zhoujunlin94.example.websocket.util.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@Slf4j
@Component
public class SendToOneHandler implements MessageHandler<SendToOneRequest> {

    @Override
    public void execute(Session session, SendToOneRequest message) {
        String fromUser = WebSocketUtil.getFromUser(session);
        if (StrUtil.isBlank(fromUser)) {
            log.warn("SendToOneHandler.execute fromUser is blank");
            return;
        }
        SendToOneResponse sendToOneResponse = new SendToOneResponse().setMsgId(message.getMsgId())
                .setContent(message.getContent()).setFromUser(fromUser).setBizType(getType());
        WebSocketUtil.send(message.getTargetUser(), SendToOneResponse.TYPE, sendToOneResponse);
    }

    @Override
    public String getType() {
        return SendToOneRequest.TYPE;
    }

}
