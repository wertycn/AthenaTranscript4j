package icu.debug.ai.translate.core.document;

/**
 * 文档内容Hash id
 * <p>
 * 相同文档内容将得到相同结果
 * </p>
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 15:02
 */
public class DocumentHashId implements Id {

    private final String hash;

    public DocumentHashId(String content) {
        this.hash = String.valueOf(content.hashCode());
    }

    @Override
    public String get() {
        return hash;
    }
}
