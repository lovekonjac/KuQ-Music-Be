package com.example.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.dashscope.aigc.generation.Generation;

@Configuration
public class GenerationConfig {

    @Bean
    public Generation generation() {
        return new Generation();
    }
}
