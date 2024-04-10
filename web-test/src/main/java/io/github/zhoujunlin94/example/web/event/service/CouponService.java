package io.github.zhoujunlin94.example.web.event.service;

import io.github.zhoujunlin94.example.web.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 16:42
 * @desc
 */
@Slf4j
@Service
public class CouponService {

    @EventListener
    public void addCoupon(UserRegisterEvent event) {
        log.info("CouponService.addCoupon start...");
        log.info("[addCoupon][给用户({}) 发放优惠劵]", event.getUserName());
        log.info("CouponService.addCoupon end...");
    }

}
