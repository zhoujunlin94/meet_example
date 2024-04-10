package io.github.zhoujunlin94.example.websocket.message;

import io.github.zhoujunlin94.example.websocket.message.base.SocketResponse;
import io.github.zhoujunlin94.example.websocket.message.constant.MessageTypeEnum;
import lombok.Data;

/**
 * 用户加入群聊的通知 Message
 *
 * @author zhoujl
 */
@Data
public class UserOnLineNotice extends SocketResponse {

    public static final String TYPE = MessageTypeEnum.TO_ALL.name();

    private String fromUser;

    private String bizType = MessageTypeEnum.AUTH_RESPONSE.name();

}
