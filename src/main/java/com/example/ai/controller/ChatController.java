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

@RestController
@RequestMapping("/api")
public class ChatController {


    private final ChatClient chatClient;
    private final ChatClient openAiChatClient;
    //private final ChatClient ollamaChatClient;


    public ChatController(
            @Qualifier("originalChatClient") ChatClient chatClient,
            @Qualifier("openAiChatClient") ChatClient openAiChatClient
            //Qualifier("ollamaChatClient")ChatClient ollamaChatClient
    ){
        this.chatClient = chatClient;
        this.openAiChatClient = openAiChatClient;
        //this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message){
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
                .call()
                .content();
    }
    @GetMapping("/openai/chat")
    public String openaiChat(@RequestParam("message") String message){
        return openAiChatClient.prompt(message).call().content();
    }
//    @GetMapping("/ollama/chat")
//    public String ollamaChat(@RequestParam("message") String message){
//        return ollamaChatClient.prompt(message).call().content();
//    }


}
