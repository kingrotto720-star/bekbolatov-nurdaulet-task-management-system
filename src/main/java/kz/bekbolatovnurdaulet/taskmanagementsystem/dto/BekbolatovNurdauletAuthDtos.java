package kz.bekbolatovnurdaulet.taskmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class BekbolatovNurdauletAuthDtos {
    private BekbolatovNurdauletAuthDtos() {
    }

    public record BekbolatovNurdauletRegisterRequestDto(
            @NotBlank @Size(min = 2, max = 100) String name,
            @NotBlank @Email String email,
            @NotBlank @Size(min = 6, max = 100) String password
    ) {
    }

    public record BekbolatovNurdauletLoginRequestDto(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {
    }

    public record BekbolatovNurdauletAuthResponseDto(
            String token,
            String tokenType,
            BekbolatovNurdauletUserResponseDto user
    ) {
    }
}
