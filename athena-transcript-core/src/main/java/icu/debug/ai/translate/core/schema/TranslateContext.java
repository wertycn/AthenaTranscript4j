package icu.debug.ai.translate.core.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 翻译上下文信息对象
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 12:44
 */
@Getter
@Setter
@ToString
public class TranslateContext {

    /**
     * 源语言
     */
    protected String sourceLange;

    /**
     * 目标语言
     */
    protected String targetLange;

    /**
     * 背景信息
     */
    protected String background;

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("source_language", sourceLange);
        result.put("target_language", targetLange);
        result.put("background", background);
        return result;
    }
}
