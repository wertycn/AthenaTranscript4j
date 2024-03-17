package icu.debug.ai.translate.core.document;

import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * @author hanjinxiang@debug.icu
 * @date 2024-03-16 15:00
 */
@EqualsAndHashCode
public class DocumentUuid implements Id {

    private final UUID id;

    public DocumentUuid() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String get() {
        return id.toString();
    }
}
