package icu.debug.ai.translate.core.model;

import org.springframework.ai.chat.prompt.ChatPromptTemplate;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PromptTemplate 工厂
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 22:41
 */
public class PromptTemplateFactory {

    public static final ChatPromptTemplate SYSTEM_DEFAULT_TEMPLATE = new ChatPromptTemplate(List.of(new PromptTemplate("""
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

    private final Map<String, ChatPromptTemplate> chatTemplateMap = new ConcurrentHashMap<>();

    public Optional<ChatPromptTemplate> get(String name) {
        return Optional.ofNullable(this.chatTemplateMap.get(name));
    }

    public void registryPrompt(String name, ChatPromptTemplate template) {
        this.chatTemplateMap.put(name, template);
    }

    public ChatPromptTemplate getDefault() {
        return get("default").orElse(SYSTEM_DEFAULT_TEMPLATE);
    }
}
