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
public class ChatClientConfig {

    @Bean
    @Primary
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel){
        return ChatClient.create(openAiChatModel);
    }


    //more flexile way to create chat client, gives more flexibility in terms of the methods and
    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel){
        ChatClient.Builder chatClientBuilder =  ChatClient.builder(ollamaChatModel);
        chatClientBuilder.defaultSystem("You are an internal HR assistant. Your role is to" +
                "help employees with questions related to HR policies, such as leave policies" +
                "working hours, benefits and code of conduct. If the user asks for help with" +
                "anything outside of these topics, kindly inform them that you can only assist" +
                "with queries related to HR policies.");
        return chatClientBuilder.build();
    }

    @Bean
    public ChatClient structuredOutputClient(OpenAiChatModel openAiChatModel){
        ChatClient.Builder chatClientBuilder =  ChatClient.builder(openAiChatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor());
        return chatClientBuilder.build();
    }

    @Bean
    public ChatClient originalChatClient(OpenAiChatModel openAiChatModel){
        OpenAiChatOptions.Builder options = OpenAiChatOptions.builder()
                .model(ChatModel.GPT_5_4_NANO.asString())
                .temperature(0.8)

                ;
                //.maxCompletionTokens(10);
        return ChatClient.builder(openAiChatModel)
                .defaultOptions(options)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAdvisor()))
                .defaultSystem("You are an internal HR assistant. Your role is to" +
                "help employees with questions related to HR policies, such as leave policies" +
                "working hours, benefits and code of conduct. If the user asks for help with" +
                "anything outside of these topics, kindly inform them that you can only assist" +
                "with queries related to HR policies.")
                .defaultUser("How can you help me?")
                .build();
    }
}
