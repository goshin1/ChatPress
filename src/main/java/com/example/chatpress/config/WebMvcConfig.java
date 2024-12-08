package com.example.chatpress.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // window C:\chatpress\images
<<<<<<< HEAD
//        registry.addResourceHandler("/attach/images/**")
//                .addResourceLocations("file:///C:/chatpress/images/");
//        registry.addResourceHandler("/attach/images/icons/**")
//                .addResourceLocations("file:///C:/chatpress/images/icons/");
=======
        registry.addResourceHandler("/attach/images/**")
                .addResourceLocations("file:////root/ChatPress/build/libs/images/");
        registry.addResourceHandler("/attach/images/icons/**")
                .addResourceLocations("file:////root/ChatPress/build/libs/images/icons/");
>>>>>>> ca28459b8f9723fdfdea98152ce3285c9006dda6


        // linux
        registry.addResourceHandler("/attach/images/**")
                .addResourceLocations("file:///home/ubuntu/images/");
        registry.addResourceHandler("/attach/images/icons/**")
                .addResourceLocations("file:///home/ubuntu/images/icons/");
    }

}
