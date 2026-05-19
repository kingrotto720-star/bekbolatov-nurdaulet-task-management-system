package kz.bekbolatovnurdaulet.taskmanagementsystem.dto;

import java.time.LocalDateTime;

public record BekbolatovNurdauletUserResponseDto(
        Long id,
        String name,
        String email,
        String role,
        LocalDateTime createdAt
) {
}
