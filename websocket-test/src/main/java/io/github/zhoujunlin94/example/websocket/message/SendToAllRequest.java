package io.github.zhoujunlin94.example.websocket.message;

import io.github.zhoujunlin94.example.websocket.message.base.SocketRequest;
import io.github.zhoujunlin94.example.websocket.message.constant.MessageTypeEnum;
import lombok.Data;

/**
 * 发送给所有人的群聊消息的 Message
 *
 * @author zhoujunlin
 */
@Data
public class SendToAllRequest extends SocketRequest {

    public static final String TYPE = MessageTypeEnum.SEND_TO_ALL_REQUEST.name();

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;

    @Override
    public String getType() {
        return TYPE;
    }
}
