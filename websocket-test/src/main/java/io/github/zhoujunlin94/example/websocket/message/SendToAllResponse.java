package io.github.zhoujunlin94.example.websocket.message;

import io.github.zhoujunlin94.example.websocket.message.base.SocketResponse;
import io.github.zhoujunlin94.example.websocket.message.constant.MessageTypeEnum;
import lombok.Data;

/**
 * 发送消息响应结果的 Message
 */
@Data
public class SendToAllResponse extends SocketResponse {

    public static final String TYPE = MessageTypeEnum.TO_ALL.name();

    /**
     * 消息来源用户
     */
    private String fromUser;
    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;

    private String bizType;

}
