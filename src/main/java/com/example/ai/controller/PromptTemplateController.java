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
public class PromptTemplateController {

    @Value("classpath:/PromptTemplate/userPromptTemplate.st")
    Resource promptTemplate;

    private final ChatClient chatClient;

    public PromptTemplateController(@Qualifier("originalChatClient") ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/email")
    public String emailResponse(
            @RequestParam("customerMessage") String customerMessage,
            @RequestParam("customerName") String customerName){
        //overriding the default system message.
        return chatClient
                .prompt()
                .system("""
                        You are professional customer service assistant which helps drafting email
                        responses to improve the productivity  of the customer support team.
                        """)
                .user(promptTemplateSpec ->
                        promptTemplateSpec.text(promptTemplate)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage)
                )
                .call()
                .content();
    }
}
