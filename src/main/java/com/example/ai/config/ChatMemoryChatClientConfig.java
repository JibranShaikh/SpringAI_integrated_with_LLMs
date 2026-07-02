package com.example.ai.config;


import com.example.ai.advisors.TokenUsageAdvisor;
import com.openai.models.ChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class ChatMemoryChatClientConfig {

    @Bean("chatMemoryChatClient")
    public ChatClient originalChatClient(OpenAiChatModel openAiChatModel){
        return ChatClient.builder(openAiChatModel)
                .build();
    }
}
