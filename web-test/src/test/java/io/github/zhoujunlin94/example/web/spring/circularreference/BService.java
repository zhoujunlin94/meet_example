package io.github.zhoujunlin94.example.web.spring.circularreference;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author zhoujunlin
 * @date 2024年03月25日 11:34
 * @desc
 */
@Setter
@Service
@RequiredArgsConstructor
public class BService {

//    @Autowired
//    private AService aService;

    private final AService aService;

    public static void main(String[] args) {
        /*AService aService = new AService();
        BService bService = new BService();
        bService.setAService(aService);
        aService.setBService(bService);

        System.out.println(aService);
        System.out.println(bService);*/
    }

}
