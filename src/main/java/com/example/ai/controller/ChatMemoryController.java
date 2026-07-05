package com.example.ai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatMemoryController {

    private final ChatClient chatClient;

    public ChatMemoryController(@Qualifier("chatMemoryChatClient") ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/chat-memory/{identifier}")
    public ResponseEntity<String> chatMemory(
            @RequestParam("message") String message,
            @PathVariable("identifier") String identifier){
        //overriding the default system message.
        String content = chatClient
                .prompt()
                //here basically all the conversations that are going to happen will get the same
                //convesation ids.
                .advisors(advisorSpec -> {
                    advisorSpec.param(ChatMemory.CONVERSATION_ID, identifier);
                })
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(content);
    }
}
