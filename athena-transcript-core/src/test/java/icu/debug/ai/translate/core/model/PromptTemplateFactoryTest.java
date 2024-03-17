package icu.debug.ai.translate.core.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.ChatPromptTemplate;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 23:02
 */
@DisplayName("Prompt模板工厂测试")
class PromptTemplateFactoryTest {

    public static final ChatPromptTemplate TEMPLATE = new ChatPromptTemplate(List.of(new PromptTemplate("""
            你是一款专业的技术文档翻译程序，你需要帮我将`{source_language}`文档翻译为`{target_language}`
            注意:
              1. 只翻译文本内容，保持文本格式不变，不需要补全格式
              2. 只输出翻译结果，不做解释
              3. 翻译过程保持技术文档的专业性，不翻译专有名词
              4. 翻译的结果需要符合`{target_language}`语言的阅读习惯，避免口语化
              
              {background}
              以下是需要翻译的内容:
              ````content
              {content}
              ````
            """)));

    @SneakyThrows
    @Test
    @DisplayName("Prompt注册测试")
    void testPromptRegistry() {
        PromptTemplateFactory factory = new PromptTemplateFactory();
        factory.registryPrompt("test", TEMPLATE);
        Optional<ChatPromptTemplate> templateOptional = factory.get("test");
        assertTrue(templateOptional.isPresent());
        ChatPromptTemplate template = templateOptional.get();
        String content = "hello!  athena transcript~";
        Prompt prompt = template.create(Map.of(
                "source_language", "英文",
                "target_language", "中文",
                "background", "",
                "content", content)
        );

        String exp = """
                你是一款专业的技术文档翻译程序，你需要帮我将`英文`文档翻译为`中文`
                注意:
                  1. 只翻译文本内容，保持文本格式不变，不需要补全格式
                  2. 只输出翻译结果，不做解释
                  3. 翻译过程保持技术文档的专业性，不翻译专有名词
                  4. 翻译的结果需要符合`中文`语言的阅读习惯，避免口语化
                                
                  以下是需要翻译的内容:
                  ````content
                  hello!  athena transcript~
                  ````
                """;

        assertEquals(exp, prompt.getContents().replace(System.getProperty("line.separator"), "\n"));
    }

}