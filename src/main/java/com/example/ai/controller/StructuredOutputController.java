package com.example.ai.controller;

import com.example.ai.advisors.TokenUsageAdvisor;
import com.example.ai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputController {


    private final ChatClient chatClient;


    public StructuredOutputController(@Qualifier("structuredOutputClient") ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> chatBean(@RequestParam("question") String question){
        //overriding the default system message.
        CountryCities countryCities =  chatClient
                .prompt()
                //.advisors(new TokenUsageAdvisor())
                .user(question)
                .call()
                .entity(CountryCities.class);
                //.entity(new BeanOutputConverter<>(CountryCities.class));
        return ResponseEntity.ok(countryCities);

    }

    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> chatList(@RequestParam("question") String question){
        //overriding the default system message.
        List<String> countryCities =  chatClient
                .prompt()
                //.advisors(new TokenUsageAdvisor())
                .user(question)
                .call()
                .entity(new ListOutputConverter());
        return ResponseEntity.ok(countryCities);

    }

    @GetMapping("/chat-map")
    public ResponseEntity<Map<String, Object>> chatMap(@RequestParam("question") String question){
        //overriding the default system message.
        Map<String, Object> countryCities =  chatClient
                .prompt()
                //.advisors(new TokenUsageAdvisor())
                .user(question)
                .call()
                .entity(new MapOutputConverter());
        return ResponseEntity.ok(countryCities);

    }

    @GetMapping("/chat-bean-list")
    public ResponseEntity<List<CountryCities>> chatBeanList(@RequestParam("question") String question){
        //overriding the default system message.
        List<CountryCities> countryCities =  chatClient
                .prompt()
                //.advisors(new TokenUsageAdvisor())
                .user(question)
                .call()
                .entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
        return ResponseEntity.ok(countryCities);

    }





}
