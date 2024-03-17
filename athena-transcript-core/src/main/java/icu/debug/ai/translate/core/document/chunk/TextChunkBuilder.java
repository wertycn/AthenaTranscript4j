package icu.debug.ai.translate.core.document.chunk;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 11:04
 */
public class TextChunkBuilder implements ChunkBuilder {

    protected final String delimiter;

    /**
     * 分块大小
     */
    protected final int chunkSize;

    protected List<DocumentChunk> chunks = new ArrayList<>();

    /**
     * 当前文本块指针对象
     */
    protected ChunkPointer thatChunkPointer;

    public TextChunkBuilder(String delimiter, int chunkSize) {
        this.delimiter = delimiter;
        this.chunkSize = chunkSize;
    }

    public ChunkBuilder appendAll(String content) {
        for (String line : content.split(delimiter)) {
            append(line);
        }
        return this;
    }

    @Override
    public ChunkBuilder append(String line) {
        if (!isInit()) {
            initTextChunk();
        }
        // TODO: 一行文本就超过chunk size 情况暂不处理
        if (this.thatChunkPointer.getSize() + line.length() > chunkSize) {
            completeChunk();
            initTextChunk();
        }

        this.thatChunkPointer.add(line);
        return this;
    }

    protected boolean isInit() {
        return thatChunkPointer != null;
    }

    @Override
    public List<DocumentChunk> build() {
        if (isInit()) {
            completeChunk();
        }
        return chunks;
    }

    protected void completeChunk() {
        if (this.thatChunkPointer.getSize() > 0) {
            List<DocumentChunk> chunks = this.thatChunkPointer.buildDocumentChunk();
            this.chunks.addAll(chunks);
        }

        this.thatChunkPointer = null;
    }

    protected void initTextChunk() {
        this.thatChunkPointer = new ChunkPointer("text", "\n", true);
    }


}
