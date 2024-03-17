package icu.debug.ai.translate.core.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import icu.debug.ai.translate.core.helper.FileHelper;
import icu.debug.ai.translate.core.schema.TranscriptDocument;
import icu.debug.ai.translate.core.schema.TranscriptResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static icu.debug.ai.translate.core.MockFactory.getMockContent;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 19:22
 */
@DisplayName("简单Markdown文档处理器测试")
class SimpleMarkdownDocumentProcessTest {

    @Test
    @DisplayName("实例化创建测试")
    void testInstanceCreate() throws JsonProcessingException {
        String content = getMockContent("mock/markdown/getting_started.md");
        Document source = Document.builder().id(new DocumentUuid()).content(content).build();
        SimpleMarkdownDocumentProcess process = new SimpleMarkdownDocumentProcess(source);
        List<TranscriptDocument> documents = process.resolve(500);
        //原样返回 ， 翻译重新组合后，应该得到相同的文档内容
        List<TranscriptResult> results = documents.stream().map(item -> new TranscriptResult(item, item.getContent())).toList();
        Document result = process.recover(results);
        assertEquals(source.getContent(), result.getContent());

    }



}