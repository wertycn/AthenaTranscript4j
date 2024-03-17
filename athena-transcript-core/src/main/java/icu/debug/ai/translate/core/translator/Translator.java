package icu.debug.ai.translate.core.translator;

import icu.debug.ai.translate.core.schema.TranscriptDocument;
import icu.debug.ai.translate.core.schema.TranscriptResult;

import java.util.List;

/**
 * 翻译器
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 18:28
 */
public interface Translator {

    default List<TranscriptResult> translate(List<TranscriptDocument> documents) {
        return documents.stream().map(this::translate).toList();
    }

    /**
     * 文档翻译
     *
     * @param document
     * @return
     */
    TranscriptResult translate(TranscriptDocument document);
}
