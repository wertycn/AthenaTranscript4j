package icu.debug.ai.translate.core.translator;

import icu.debug.ai.translate.core.schema.TranscriptDocument;
import icu.debug.ai.translate.core.schema.TranscriptResult;
import icu.debug.ai.translate.core.schema.TranslateContext;

import java.util.List;

/**
 * 翻译器
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 18:28
 */
public interface Translator {

    default List<TranscriptResult> translate(List<TranscriptDocument> documents, TranslateContext context) {
        return documents.stream().map(item -> this.translate(item, context)).toList();
    }


    /**
     * @param document 待翻译内容
     * @param context  翻译上下文
     * @return
     */
    TranscriptResult translate(TranscriptDocument document, TranslateContext context);
}
