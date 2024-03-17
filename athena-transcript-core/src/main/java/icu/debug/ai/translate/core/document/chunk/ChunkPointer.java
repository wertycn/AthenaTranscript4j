package icu.debug.ai.translate.core.document.chunk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当前文本块指针对象
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 11:29
 */
@Getter
public class ChunkPointer {

    /**
     * 块类型
     */
    private String type;

    private int size = 0;

    private final String delimiter;
    private final boolean translate;

    /**
     * 块内容列表
     */
    @Getter
    private List<String> lines = new ArrayList<>();

    private Map<String, String> metadata = new HashMap<>();

    public ChunkPointer(String type, String delimiter, boolean translate) {
        this.type = type;
        this.delimiter = delimiter;
        this.translate = translate;
    }

    public void add(String line) {
        Assert.notNull(line, "add line it must not null");
        this.lines.add(line);
        this.size += line.length();
    }

    public void putMetadata(String key, String value) {
        this.metadata.put(key, value);
    }

    public List<DocumentChunk> buildDocumentChunk() {
        List<DocumentChunk> chunks = new ArrayList<>();
        // 解析出有内容的边界 用于过滤文本块前后的空白行
        Boundary boundary = resolveHasContentBoundary();


        // 判断是为全空内容块
        if (!boundary.hasContent()) {
            String content = String.join(delimiter, lines);
            DocumentChunk chunk = buildBlankChunk(content);
            chunks.add(chunk);
            return chunks;
        }

        // 判断块前是否有空行
        if (!boundary.isMinStart()) {
            String content = String.join(delimiter, lines.subList(0, boundary.start));
            DocumentChunk chunk = buildBlankChunk(content);
            chunks.add(chunk);
        }
        String content = String.join(delimiter, lines.subList(boundary.start, boundary.end));
        chunks.add(DocumentChunk.createUuidChunk(content, type, translate));

        // 判断结尾是否有空行
        if (!boundary.isMaxEnd()) {
            content = String.join(delimiter, lines.subList(boundary.end, boundary.max));
            chunks.add(buildBlankChunk(content));
        }
        return chunks;
    }

    private static DocumentChunk buildBlankChunk(String content) {
        return DocumentChunk.createUuidChunk(content, "blank", false);
    }

    private Boundary resolveHasContentBoundary() {
        // 过滤内容首位的空行
        int start = -1;
        int end = -1;
        int length = lines.size();
        for (int i = 0; i < length; i++) {
            // 判断从第几个开始不是空行
            if (start == -1 && StringUtils.hasLength(lines.get(i).trim())) {
                start = i;
            }
            //
            int endIndex = length - i;
            if (end == -1 && StringUtils.hasLength(lines.get(endIndex - 1).trim())) {
                end = endIndex;
            }
            // 找到后就终止循环
            if (start != -1 && end != -1) {
                break;
            }
        }
        Boundary boundary = new Boundary(start, end, length);
        return boundary;
    }

    protected boolean isTextChunk() {
        return this.isType("text");
    }

    protected boolean isType(String type) {
        return this.type.equals(type);
    }

    @AllArgsConstructor
    @Getter
    protected class Boundary {
        private int start;
        private int end;

        private int max;

        public boolean isMinStart() {
            return start == 0;
        }

        public boolean isMaxEnd() {
            return end == max ;
        }

        public boolean hasContent() {
            return !(start == end && start == -1);
        }
    }


}
