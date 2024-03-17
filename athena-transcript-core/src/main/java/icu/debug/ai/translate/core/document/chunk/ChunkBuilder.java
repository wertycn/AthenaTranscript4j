package icu.debug.ai.translate.core.document.chunk;

import java.util.List;

/**
 * 文档块建造者
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 10:39
 */
public interface ChunkBuilder {


    /**
     * 添加新行
     *
     * @param line
     * @return
     */
    ChunkBuilder append(String line);


    /**
     * @return
     */
    List<DocumentChunk> build();


}
