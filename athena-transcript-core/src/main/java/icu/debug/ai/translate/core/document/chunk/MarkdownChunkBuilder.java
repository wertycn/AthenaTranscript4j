package icu.debug.ai.translate.core.document.chunk;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 12:45
 */
public class MarkdownChunkBuilder extends TextChunkBuilder {

    public MarkdownChunkBuilder(String delimiter, int chunkSize) {
        super(delimiter, chunkSize);
    }


    public ChunkBuilder append(String line) {
        if (!isInit()) {
            initChunk(line);
        }
        // TODO: 一行文本就超过chunk size 情况暂不处理
        if (this.thatChunkPointer.isTranslate() && this.thatChunkPointer.getSize() + line.length() > chunkSize) {
            completeChunk();
            initTextChunk();
        }
        // 如果当前块是代码块，找结束标记
        if (this.thatChunkPointer.isType("code")) {
            //  获取结束标记
            String mark = this.thatChunkPointer.getMetadata().get("mark");
            if (line.startsWith(mark)) {
                this.thatChunkPointer.add(line);
                this.completeChunk();
                return this;
            }
        }

        // 如果当前是文本块， 判断当前行类型 确定当前行类型
        if (this.thatChunkPointer.isTextChunk()) {
            ChunkType chunkType = resolveType(line);
            if (chunkType.isCode()) {
                this.completeChunk();
                this.initChunk(chunkType);
            }
        }
        this.thatChunkPointer.add(line);

        return this;
    }

    public void initChunk(String line) {
        ChunkType chunkType = resolveType(line);
        initChunk(chunkType);
    }

    private void initChunk(ChunkType chunkType) {
        this.thatChunkPointer = new ChunkPointer(chunkType.getType(), "\n", chunkType.isText());
        if (chunkType.isCode()) {
            thatChunkPointer.putMetadata("mark", chunkType.getMark());
        }
    }

    private ChunkType resolveType(String line) {
        if (isBigCodeChunk(line)) {
            return new ChunkType("code", line.substring(0, 4));
        }
        if (isCodeChunk(line)) {
            return new ChunkType("code", line.substring(0, 3));
        }
        // TODO: MDX,等单行文本块支持


        return new ChunkType("text", null);
    }

    private static boolean isBigCodeChunk(String line) {
        return line.startsWith("````") || line.startsWith("~~~~") || line.startsWith("::::");
    }

    private boolean isCodeChunk(String line) {
        return line.startsWith("```") || line.startsWith("~~~") || line.startsWith(":::");
    }

    @AllArgsConstructor
    @Getter
    private class ChunkType {
        private String type;
        private String mark;

        public boolean isCode() {
            return type.equals("code");
        }

        public boolean isText() {
            return type.equals("text");
        }


    }

}
