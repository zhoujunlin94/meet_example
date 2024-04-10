package org.springframework.boot;

import com.google.common.collect.ImmutableMap;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * @author zhoujunlin
 * @date 2024年03月29日 11:52
 * @desc
 */
public class Step04 {

    public static void main(String[] args) {
        SpringApplicationBannerPrinter bannerPrinter = new SpringApplicationBannerPrinter(
                new DefaultResourceLoader(),
                new SpringBootBanner()
        );
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();

        // 文字banner
        applicationEnvironment.getPropertySources().addLast(new MapPropertySource("custom", ImmutableMap.of("spring.banner.location", "banner1.txt")));

        // 图片banner
        //applicationEnvironment.getPropertySources().addLast(new MapPropertySource("custom", ImmutableMap.of("spring.banner.image.location", "banner1.png")));

        System.out.println("SpringBootVersion:" + SpringBootVersion.getVersion());
        bannerPrinter.print(applicationEnvironment, Step04.class, System.out);
    }


}
