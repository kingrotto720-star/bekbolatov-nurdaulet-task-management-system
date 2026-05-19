package kz.bekbolatovnurdaulet.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTaskPriority;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTaskStatus;

public final class BekbolatovNurdauletTaskDtos {
    private BekbolatovNurdauletTaskDtos() {
    }

    public record BekbolatovNurdauletTaskRequestDto(
            @NotBlank @Size(max = 200) String title,
            @Size(max = 3000) String description,
            @NotNull BekbolatovNurdauletTaskStatus status,
            @NotNull BekbolatovNurdauletTaskPriority priority,
            @NotNull LocalDate deadline,
            @NotNull Long projectId,
            @NotNull Long assignedUserId
    ) {
    }

    public record BekbolatovNurdauletTaskResponseDto(
            Long id,
            String title,
            String description,
            BekbolatovNurdauletTaskStatus status,
            BekbolatovNurdauletTaskPriority priority,
            LocalDate deadline,
            LocalDateTime createdAt,
            Long projectId,
            String projectName,
            Long assignedUserId,
            String assignedUserName
    ) {
    }
}
