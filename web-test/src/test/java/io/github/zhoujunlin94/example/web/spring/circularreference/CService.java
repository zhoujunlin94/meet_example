package io.github.zhoujunlin94.example.web.spring.circularreference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhoujunlin
 * @date 2024年03月25日 11:43
 * @desc
 */
@Service
public class CService {

    @Autowired
    private CService cService;

}
