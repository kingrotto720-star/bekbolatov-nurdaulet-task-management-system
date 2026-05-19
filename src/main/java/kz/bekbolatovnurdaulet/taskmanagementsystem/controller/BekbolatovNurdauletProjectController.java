package kz.bekbolatovnurdaulet.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import java.util.List;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletProjectDtos.BekbolatovNurdauletProjectRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletProjectDtos.BekbolatovNurdauletProjectResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.service.BekbolatovNurdauletProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class BekbolatovNurdauletProjectController {
    private final BekbolatovNurdauletProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BekbolatovNurdauletProjectResponseDto> create(
            @Valid @RequestBody BekbolatovNurdauletProjectRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<BekbolatovNurdauletProjectResponseDto>> getAll() {
        return ResponseEntity.ok(projectService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<BekbolatovNurdauletProjectResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BekbolatovNurdauletProjectResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody BekbolatovNurdauletProjectRequestDto request) {
        return ResponseEntity.ok(projectService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
