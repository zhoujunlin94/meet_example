package io.github.zhoujunlin94.example.web.config.json;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextObjectSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023年04月22日 9:51
 * @desc int类型序列化器
 * 0->null
 */
@Slf4j
public class ConvertIntegerSerializer implements ContextObjectSerializer {

    @Override
    public void write(JSONSerializer jsonSerializer, Object o, BeanContext beanContext) throws IOException {
    }

    @Override
    public void write(JSONSerializer jsonSerializer, Object value, Object key, Type type, int i) throws IOException {
        if (Objects.nonNull(value) && value instanceof Integer) {
            Integer intValue = (Integer) value;
            value = intValue == 0 ? null : intValue;
        }
        jsonSerializer.write(value);
    }
}
