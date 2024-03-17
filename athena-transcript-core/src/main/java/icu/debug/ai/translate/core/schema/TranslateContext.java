package icu.debug.ai.translate.core.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
