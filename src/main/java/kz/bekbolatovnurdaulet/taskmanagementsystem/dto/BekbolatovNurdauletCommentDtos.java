package kz.bekbolatovnurdaulet.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public final class BekbolatovNurdauletCommentDtos {
    private BekbolatovNurdauletCommentDtos() {
    }

    public record BekbolatovNurdauletCommentRequestDto(
            @NotBlank @Size(max = 2000) String message,
            @NotNull Long taskId,
            @NotNull Long userId
    ) {
    }

    public record BekbolatovNurdauletCommentResponseDto(
            Long id,
            String message,
            LocalDateTime createdAt,
            Long taskId,
            Long userId,
            String userName
    ) {
    }
}
