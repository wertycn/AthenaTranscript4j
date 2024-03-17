package icu.debug.ai.translate.core.schema;

import icu.debug.ai.translate.core.document.Id;
import lombok.Getter;

/**
 * Athena transcript 基础翻译对象
 * <p>
 * 不同格式文档均转换为TranscriptDocument格式后统一进行翻译处理
 *
 * </p>
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 12:47
 */
@Getter
public class TranscriptDocument {

    /**
     * 唯一标识
     */
    private final Id id;

    /**
     * 内容
     */
    private final String content;

    /**
     * 文档格式
     */
    private final String format;

    private final String hash;

    public TranscriptDocument(Id id, String content, String format) {
        this.id = id;
        this.content = content;
        this.format = format;
        this.hash = String.valueOf(content.hashCode());
    }

}
