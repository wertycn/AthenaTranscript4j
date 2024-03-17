package icu.debug.ai.translate.core.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件翻译上下文
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 12:43
 */
@Getter
@Setter
@ToString
public class FileTranslateContext extends TranslateContext {

    private String sourcePath;

    private String targetPath;

}
