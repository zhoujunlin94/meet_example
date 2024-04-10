package io.github.zhoujunlin94.example.web;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年05月10日 17:22
 * @desc
 */
public class StringTest {

    @Test
    public void testFormat() {
        String text = "{name} is a {job} and {desc}";
        Map<String, String> paramMap = ImmutableMap.of("job", "programmer", "name", "zhoujunlin", "desc", "poorman");
        String result = StrUtil.format(text, paramMap);
        System.out.println(text);
        System.out.println(result);

        System.out.println("打印堆栈调用");

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(stackTraceElement.getClassName() + "#" + stackTraceElement.getMethodName());
        }

    }

}
