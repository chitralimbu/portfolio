package com.chitra.service.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfig {

    @Bean
    public UserEventHandler userEventHandler(){
        return new UserEventHandler();
    }

}
