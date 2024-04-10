package io.github.zhoujunlin94.example.websocket.message.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 基础消息体
 *
 * @author zhoujl
 */
public class SocketMessage implements Serializable {

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
