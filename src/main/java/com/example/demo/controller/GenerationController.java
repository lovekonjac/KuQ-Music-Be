package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Semaphore;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import io.reactivex.Flowable;

@RestController
class GenerationController {

    private static final Logger logger = LoggerFactory.getLogger(GenerationController.class);

    private final Generation generation;

    public GenerationController(Generation generation) {
        this.generation = generation;
    }

    @GetMapping("/chat/stream")
    public Flux<String> streamGenerationResult(@RequestParam String userMessage) {
        return streamCall(userMessage);
    }

    public Flux<String> streamCall(String userMessage) {
        Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(userMessage).build();

        GenerationParam param = GenerationParam.builder()
                .model("qwen-turbo")
                .messages(Arrays.asList(userMsg))
                .apiKey("sk-bf128ace8cc4421b801c5e5be32c0bef")
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .incrementalOutput(true)
                .build();

        Flowable<GenerationResult> result;
        try {
            result = generation.streamCall(param);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            logger.error("Failed to initiate stream call: {}", e.getMessage());
            sink.tryEmitError(e);
            return sink.asFlux();
        }

        result.subscribe(
                message -> {
                    String content = message.getOutput().getChoices().get(0).getMessage().getContent();
                    sink.tryEmitNext(content);
                    logger.info("Received message: {}", content);
                },
                throwable -> {
                    sink.tryEmitError(throwable);
                    logger.error("Exception occurred: {}", throwable.getMessage());
                },
                sink::tryEmitComplete
        );

        return sink.asFlux();
    }
}