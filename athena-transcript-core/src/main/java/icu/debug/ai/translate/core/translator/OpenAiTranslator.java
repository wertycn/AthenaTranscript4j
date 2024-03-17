package icu.debug.ai.translate.core.translator;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 18:31
 */
public class OpenAiTranslator extends BaseSpringAiTranslator implements Translator {

    OpenAiTranslator(PromptTemplate promptTemplate, OpenAiChatClient chatClient) {
        super(promptTemplate, chatClient);
    }

}
