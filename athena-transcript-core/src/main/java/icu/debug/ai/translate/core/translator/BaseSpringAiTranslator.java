package icu.debug.ai.translate.core.translator;

import icu.debug.ai.translate.core.model.PromptTemplateFactory;
import icu.debug.ai.translate.core.schema.TranscriptDocument;
import icu.debug.ai.translate.core.schema.TranscriptResult;
import icu.debug.ai.translate.core.schema.TranslateContext;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.ChatPromptTemplate;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.Map;
import java.util.Optional;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 18:37
 */
class BaseSpringAiTranslator {

    private final PromptTemplateFactory templateFactory;

    private final ChatClient chatClient;

    BaseSpringAiTranslator(PromptTemplateFactory templateFactory, ChatClient chatClient) {
        this.templateFactory = templateFactory;
        this.chatClient = chatClient;
    }

    public TranscriptResult translate(TranscriptDocument document, TranslateContext context) {
        return new TranscriptResult(document, translate(document.getContent(), context));
    }

    public String translate(String content, TranslateContext context) {
        ChatResponse chatResponse = requestModel(content, context);
        return resolveResponse(chatResponse);
    }

    public ChatResponse requestModel(String content, TranslateContext context) {
        ChatPromptTemplate template = templateFactory.getDefault();
        Map<String, Object> model = context.toMap();
        model.put("content", content);
        Prompt prompt = template
                .create(model);
        ChatResponse chatResponse = this.chatClient.call(prompt);
        return chatResponse;
    }

    public static String resolveResponse(ChatResponse chatResponse) {
        return chatResponse.getResult().getOutput().getContent();
    }


}
