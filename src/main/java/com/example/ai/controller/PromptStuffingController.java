package com.example.ai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptStuffingController {

    @Value("classpath:/PromptTemplate/systemPromptTemplate.st")
    Resource stuffPromptTemplate;

    private final ChatClient chatClient;

    public PromptStuffingController(@Qualifier("originalChatClient") ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/prompt-stuffing")
    public String emailResponse(@RequestParam("message") String message){
        //overriding the default system message.
        return chatClient
                .prompt()
                .system(stuffPromptTemplate)
                .user(message)
                .call()
                .content();
    }
}
