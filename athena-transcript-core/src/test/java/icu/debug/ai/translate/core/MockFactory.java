package icu.debug.ai.translate.core;

import icu.debug.ai.translate.core.helper.FileHelper;
import lombok.SneakyThrows;

/**
 * mock 数据工厂
 *
 * @author hanjinxiang@debug.icu
 * @date 2024-03-17 12:11
 */
public class MockFactory {

    @SneakyThrows
    public static String getMockContent(String path) {
        return FileHelper.readResource(path);
    }

}
