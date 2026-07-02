package com.example.ai.controller;

import com.example.ai.advisors.TokenUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class StreamController {


    private final ChatClient chatClient;
    private final ChatClient openAiChatClient;
    private final ChatClient ollamaChatClient;


    public StreamController(
            @Qualifier("originalChatClient") ChatClient chatClient,
            @Qualifier("openAiChatClient") ChatClient openAiChatClient,
            @Qualifier("ollamaChatClient")ChatClient ollamaChatClient
    ){
        this.chatClient = chatClient;
        this.openAiChatClient = openAiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/stream")
    public Flux<String> chatStream(@RequestParam("message") String message){
        //overriding the default system message.
        return chatClient
                .prompt()
                //.advisors(new TokenUsageAdvisor())
                .system("You are an internal IT helpdesk assistant. Your role is to assist employees" +
                        "with IT-related issues such as resetting passwords, unlocking accounts, and" +
                        "answering questions related to IT policies. If  a user requests  help with anything" +
                        "outside of these responsibilities, respond  politely and inform them that you are " +
                        "only able to assist with IT support tasks within your defined scope.")
                .user(message)
                .stream()
                .content();
    }

}
