package icu.debug.ai.translate.core.translator;

import icu.debug.ai.translate.core.schema.TranscriptDocument;
import icu.debug.ai.translate.core.schema.TranscriptResult;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.Map;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 18:37
 */
class BaseSpringAiTranslator {

    private final PromptTemplate promptTemplate;

    private final ChatClient chatClient;

    BaseSpringAiTranslator(PromptTemplate promptTemplate, ChatClient chatClient) {
        this.promptTemplate = promptTemplate;
        this.chatClient = chatClient;
    }

    public TranscriptResult translate(TranscriptDocument document) {
        return new TranscriptResult(document, translate(document.getContent()));
    }

    public String translate(String content) {
        ChatResponse chatResponse = requestModel(content);
        return resolveResponse(chatResponse);
    }

    public ChatResponse requestModel(String content) {
        Prompt prompt = promptTemplate.create(Map.of("content", content));
        ChatResponse chatResponse = this.chatClient.call(prompt);
        return chatResponse;
    }

    public static String resolveResponse(ChatResponse chatResponse) {
        return chatResponse.getResult().getOutput().getContent();
    }


}
