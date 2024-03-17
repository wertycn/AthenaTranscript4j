package icu.debug.ai.translate.core.document.chunk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static icu.debug.ai.translate.core.MockFactory.getMockContent;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 11:05
 */
@DisplayName("文本分块测试")
class TextChunkBuilderTest {

    public static final String DELIMITER = "\r\n";

    @Test
    @DisplayName("声明文本块实例需要声明块最大长度")
    void testTextChunkInstanceCreate() {
        TextChunkBuilder builder = new TextChunkBuilder(DELIMITER, 500);
        assertNotNull(builder);
    }

    @Test
    @DisplayName("测试支持按行传入文本")
    void testBuilderAppend() {
        TextChunkBuilder builder = new TextChunkBuilder(DELIMITER, 500);
        builder.append("123123");
        List<DocumentChunk> chunks = builder.build();
        assertEquals(1, chunks.size());
        assertEquals("123123", chunks.get(0).getContent());
    }

    @Test
    @DisplayName("需要支持块大小切分")
    void testChunkSizeHandler() {
        TextChunkBuilder builder = new TextChunkBuilder(DELIMITER, 10);
        builder.append("12345678901");
        builder.append("12345678901");
        builder.append("abcdefghig");
        List<DocumentChunk> chunks = builder.build();

        assertEquals(3, chunks.size());
        assertEquals("12345678901", chunks.get(0).getContent());
        assertEquals("12345678901", chunks.get(1).getContent());
        assertEquals("abcdefghig", chunks.get(2).getContent());
    }

    @Test
    @DisplayName("大文档切分测试")
    void testFileSplitChunk() {
        String content = getMockContent("mock/markdown/getting_started.md");
        TextChunkBuilder builder = new TextChunkBuilder(DELIMITER, 500);
        String[] split = content.split("\\r?\\n");
        for (String line : split) {
            builder.append(line);
        }
        List<DocumentChunk> chunks = builder.build();
        assertEquals(63, chunks.size());
    }

    @Test
    @DisplayName("markdown 分块测试")
    void testMarkdownSplitChunk() {
        String content = getMockContent("mock/markdown/getting_started.md");
        MarkdownChunkBuilder builder = new MarkdownChunkBuilder(DELIMITER, 500);
        String[] split = content.split("\\r?\\n");
        for (String line : split) {
            builder.append(line);
        }
        List<DocumentChunk> chunks = builder.build();
        assertEquals(123,chunks.size());


    }

    @Test
    @DisplayName("首尾空白行处理测试")
    void testStartEndBlankHandle() {
        ChunkPointer chunkPointer = new ChunkPointer("text", "\n", true);
        chunkPointer.add("");
        chunkPointer.add("");
        chunkPointer.add("xxxxx");
        chunkPointer.add("");
        chunkPointer.add("");
        List<DocumentChunk> chunks = chunkPointer.buildDocumentChunk();
        assertEquals(3, chunks.size());
        assertEquals("xxxxx", chunks.get(1).getContent());
        assertFalse(chunks.get(0).isTranslate());
        assertFalse(chunks.get(2).isTranslate());
        assertEquals("\n", chunks.get(0).getContent());
        assertEquals("\n", chunks.get(2).getContent());
    }

    @Test
    @DisplayName("首尾空白行-空格tab空行测试")
    void testStartEndBlankHandleForSpaceAndTab() {
        ChunkPointer chunkPointer = new ChunkPointer("text", "\n", true);
        chunkPointer.add(" ");
        chunkPointer.add("\t");
        chunkPointer.add("xxxxx");
        chunkPointer.add(" ");
        chunkPointer.add("xxxxx");
        chunkPointer.add("\t");
        chunkPointer.add(" ");
        List<DocumentChunk> chunks = chunkPointer.buildDocumentChunk();
        assertEquals(3, chunks.size());
        assertEquals("xxxxx\n \nxxxxx", chunks.get(1).getContent());
        assertFalse(chunks.get(0).isTranslate());
        assertFalse(chunks.get(2).isTranslate());
        assertEquals(" \n\t", chunks.get(0).getContent());
        assertEquals("\t\n ", chunks.get(2).getContent());
    }

    @Test
    @DisplayName("Markdown 空行处理测试")
    void testMarkdownBlankLine(){
        String content = getMockContent("mock/markdown/markdown_blank_line.md");
        MarkdownChunkBuilder builder = new MarkdownChunkBuilder(DELIMITER, 1000);
        ChunkBuilder chunkBuilder = builder.appendAll(content);
        List<DocumentChunk> chunks = chunkBuilder.build();
        assertEquals(3,chunks.size());
        assertEquals("text",chunks.get(0).getType());
        assertEquals("blank",chunks.get(1).getType());
        assertEquals("code",chunks.get(2).getType());
    }

}