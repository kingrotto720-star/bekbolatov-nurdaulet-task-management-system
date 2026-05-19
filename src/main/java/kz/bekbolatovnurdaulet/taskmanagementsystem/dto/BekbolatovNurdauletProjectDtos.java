package kz.bekbolatovnurdaulet.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public final class BekbolatovNurdauletProjectDtos {
    private BekbolatovNurdauletProjectDtos() {
    }

    public record BekbolatovNurdauletProjectRequestDto(
            @NotBlank @Size(max = 150) String name,
            @Size(max = 2000) String description
    ) {
    }

    public record BekbolatovNurdauletProjectResponseDto(
            Long id,
            String name,
            String description,
            LocalDateTime createdAt
    ) {
    }
}
