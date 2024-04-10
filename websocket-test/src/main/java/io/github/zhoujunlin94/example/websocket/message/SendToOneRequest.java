package io.github.zhoujunlin94.example.websocket.message;

import io.github.zhoujunlin94.example.websocket.message.base.SocketRequest;
import io.github.zhoujunlin94.example.websocket.message.constant.MessageTypeEnum;
import lombok.Data;

/**
 * 发送给指定人的私聊消息的 Message
 */
@Data
public class SendToOneRequest extends SocketRequest {

    public static final String TYPE = MessageTypeEnum.SEND_TO_ONE_REQUEST.name();

    /**
     * 发送给的用户
     */
    private String targetUser;
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
