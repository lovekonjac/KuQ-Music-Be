package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import java.lang.System;


@RestController
@Controller
public class ChatController {
    public static GenerationResult callWithMessage(String msg) throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        String prompt = msg;
        GenerationParam param = GenerationParam.builder()
                .model("qwen-turbo")
                // 如果您没有设置环境变量，则在此处您的APIKEY即可
                .apiKey("")
                .prompt(prompt)
                .build();
        return gen.call(param);
    }

//    判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/chat/callWithMessage", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest req, HttpSession session) throws NoApiKeyException, InputRequiredException {
        JSONObject jsonObject = new JSONObject();

        String msg = req.getParameter("message");
        GenerationResult s = callWithMessage(msg);
        jsonObject.put("code", 1);
        jsonObject.put("msg", JsonUtils.toJson(s.getOutput()));
        return jsonObject;
    }
}
