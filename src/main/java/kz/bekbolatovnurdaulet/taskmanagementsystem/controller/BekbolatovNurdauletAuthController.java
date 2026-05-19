package kz.bekbolatovnurdaulet.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletAuthDtos.BekbolatovNurdauletAuthResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletAuthDtos.BekbolatovNurdauletLoginRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletAuthDtos.BekbolatovNurdauletRegisterRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.service.BekbolatovNurdauletAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class BekbolatovNurdauletAuthController {
    private final BekbolatovNurdauletAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BekbolatovNurdauletAuthResponseDto> register(
            @Valid @RequestBody BekbolatovNurdauletRegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<BekbolatovNurdauletAuthResponseDto> login(
            @Valid @RequestBody BekbolatovNurdauletLoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
