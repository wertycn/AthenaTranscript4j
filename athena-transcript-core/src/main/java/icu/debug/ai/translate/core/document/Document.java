package icu.debug.ai.translate.core.document;

import lombok.*;

import java.util.Map;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 13:38
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Document {

    private Id id;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 元数据
     */
    private Map<String, String> metadata;

}
