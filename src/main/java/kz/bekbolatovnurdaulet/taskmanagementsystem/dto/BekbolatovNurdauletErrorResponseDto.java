package kz.bekbolatovnurdaulet.taskmanagementsystem.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record BekbolatovNurdauletErrorResponseDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
}
