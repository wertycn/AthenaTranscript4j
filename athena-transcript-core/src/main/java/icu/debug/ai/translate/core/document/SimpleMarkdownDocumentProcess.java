package icu.debug.ai.translate.core.document;

import icu.debug.ai.translate.core.document.chunk.DocumentChunk;
import icu.debug.ai.translate.core.document.chunk.MarkdownChunkBuilder;
import icu.debug.ai.translate.core.schema.TranscriptDocument;
import icu.debug.ai.translate.core.schema.TranscriptResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 简单markdown文档处理器
 * <p>
 * 遍历文档每一行判断其结构特性
 * </p>
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 13:49
 */
public class SimpleMarkdownDocumentProcess implements DocumentProcess {

    /**
     * 原始文档
     */
    private Document source;

    private MarkdownChunkBuilder builder;

    /**
     *
     */
    private List<DocumentChunk> chunks;

    /**
     *
     */
    private List<TranscriptDocument> transcriptDocuments;

    public SimpleMarkdownDocumentProcess(Document source) {
        this.source = source;
        //this.transcriptDocuments = markdownParse()
    }

    @Override
    public List<TranscriptDocument> resolve(int chunkLength) {
        String content = this.source.getContent();
        // 默认 builder
        MarkdownChunkBuilder builder = new MarkdownChunkBuilder("\n", chunkLength);
        builder.appendAll(content);
        this.builder = builder;
        this.chunks = builder.build();
        this.transcriptDocuments = this.chunks.stream().filter(DocumentChunk::isTranslate)
                .map(item -> new TranscriptDocument(item.getId(), item.getContent(), "markdown"))
                .toList();

        return this.transcriptDocuments;
    }

    @Override
    public Document recover(List<TranscriptResult> results) {
        Map<Id, TranscriptResult> resultMap = results.stream().collect(Collectors.toMap(item -> item.document().getId(), Function.identity(), (a, b) -> a));
        List<DocumentChunk> resultChunk = new ArrayList<>();

        for (DocumentChunk chunk : this.chunks) {
            if (chunk.isTranslate() && resultMap.containsKey(chunk.getId())) {
                TranscriptResult transcriptResult = resultMap.get(chunk.getId());
                resultChunk.add(new DocumentChunk(chunk.getId(), chunk.getType(), transcriptResult.content(), chunk.isTranslate(), chunk.getMetadata()));
            } else {
                resultChunk.add(chunk);
            }
        }

        String resultContent = resultChunk.stream().map(DocumentChunk::getContent).collect(Collectors.joining("\n"));

        return new Document(source.getId(), resultContent, source.getMetadata());
    }
}
