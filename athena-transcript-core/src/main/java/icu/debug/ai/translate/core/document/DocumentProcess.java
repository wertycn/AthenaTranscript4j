package icu.debug.ai.translate.core.document;

import icu.debug.ai.translate.core.schema.TranscriptDocument;
import icu.debug.ai.translate.core.schema.TranscriptResult;

import java.util.List;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 13:36
 */
public interface DocumentProcess {

    /**
     * 获取文档中待翻译内容列表
     *
     * @param chunkLength 待翻译的文本块的最大长度
     * @return
     */
    List<TranscriptDocument> resolve(int chunkLength);

    /**
     * 恢复为源文档
     *
     * @param results
     * @return
     */
    Document recover(List<TranscriptResult> results);

}
