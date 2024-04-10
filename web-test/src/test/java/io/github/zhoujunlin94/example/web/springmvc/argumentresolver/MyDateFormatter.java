package io.github.zhoujunlin94.example.web.springmvc.argumentresolver;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * @author zhoujunlin
 * @date 2024/3/19 22:21
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
public class MyDateFormatter implements Formatter<Date> {

    private final String desc;

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        log.warn("=====>进入了:{}", desc);
        return DateUtil.parse(text, "yyyy|MM|dd");
    }

    @Override
    public String print(Date object, Locale locale) {
        log.warn("=====>进入了:{}", desc);
        return DateUtil.format(object, "yyyy|MM|dd");
    }
}
