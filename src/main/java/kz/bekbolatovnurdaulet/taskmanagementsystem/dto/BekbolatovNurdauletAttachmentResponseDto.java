package kz.bekbolatovnurdaulet.taskmanagementsystem.dto;

import java.time.LocalDateTime;

public record BekbolatovNurdauletAttachmentResponseDto(
        Long id,
        String fileName,
        String filePath,
        LocalDateTime uploadedAt,
        Long taskId
) {
}
