package icu.debug.ai.translate.core.translator;

import icu.debug.ai.translate.core.model.PromptTemplateFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 23:55
 */
@DisplayName("OpenAI 翻译器测试")
class OpenAiTranslatorTest {


    @Test
    @DisplayName("实例化测试")
    void testInstance() {
        // TODO: 需要一个OpenAI 的mock 服务
        OpenAiApi openAiApi = new OpenAiApi("mock", "mock");
        OpenAiTranslator translator = new OpenAiTranslator(new PromptTemplateFactory(), new OpenAiChatClient(openAiApi));
    }

}