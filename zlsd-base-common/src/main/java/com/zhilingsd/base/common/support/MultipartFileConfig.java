package com.zhilingsd.base.common.support;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * MultipartFile上传配置
 * @author yuboliang
 * @date 2020-2-3
 */
@Configuration
public class MultipartFileConfig {

    public static final String TMP_FILE_DIR = "/tmp";

    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        File file = new File(TMP_FILE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }

        factory.setLocation(TMP_FILE_DIR);
        System.out.println("+++++++++++++++++++++++++++测试");
        return factory.createMultipartConfig();
    }

}