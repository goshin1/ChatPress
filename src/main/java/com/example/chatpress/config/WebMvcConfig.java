package com.example.chatpress.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // window
        registry.addResourceHandler("/attach/images/**")
                .addResourceLocations("file:///C:/ChatPress/chatpress/images/");
        registry.addResourceHandler("/attach/images/icons/**")
                .addResourceLocations("file:///C:/ChatPress/chatpress/images/icons/");

        // linux
//        registry.addResourceHandler("/attach/images/**")
//                .addResourceLocations("file:///home/ubuntu/images/");
//        registry.addResourceHandler("/attach/images/icons/**")
//                .addResourceLocations("file:///home/ubuntu/images/icons/");
    }

}
