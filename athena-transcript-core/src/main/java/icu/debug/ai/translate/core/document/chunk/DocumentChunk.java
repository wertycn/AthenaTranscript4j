package icu.debug.ai.translate.core.document.chunk;

import icu.debug.ai.translate.core.document.DocumentHashId;
import icu.debug.ai.translate.core.document.DocumentUuid;
import icu.debug.ai.translate.core.document.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 文档块
 * <p>
 * 原始文档经过处理后的文档块,对于大部分文档，都可以切分为多个文档块
 * </p>
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 14:14
 */
@Getter
@AllArgsConstructor
public class DocumentChunk {

    /**
     * 分块唯一标识
     */
    private Id id;


    /**
     * 块类型，类型由DocumentProcess自行定义管理
     */
    private String type;

    /**
     * 块内容
     */
    private String content;

    /**
     * 是否需要翻译
     */
    private boolean translate;

    /**
     * 分块元数据
     */
    private Map<String, String> metadata = new HashMap<>();

    public DocumentChunk appendMetadata(String key, String value) {
        this.metadata.put(key, value);
        return this;
    }


    public DocumentChunk appendMetadata(Map<String, String> data) {
        this.metadata.putAll(data);
        return this;
    }

    public DocumentChunk copy() {
        return new DocumentChunk(this.id, this.type, this.content, this.translate, this.metadata);
    }


    public static DocumentChunk createUuidChunk(String content, String type, boolean translate) {
        return new DocumentChunk(new DocumentUuid(), type, content, translate, new HashMap<>());
    }

    public static DocumentChunk createHashIdChunk(String content, String type, boolean translate) {
        return new DocumentChunk(new DocumentHashId(content), type, content, translate, new HashMap<>());
    }


}
