package kz.bekbolatovnurdaulet.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletTaskDtos.BekbolatovNurdauletTaskRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletTaskDtos.BekbolatovNurdauletTaskResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTaskStatus;
import kz.bekbolatovnurdaulet.taskmanagementsystem.service.BekbolatovNurdauletTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class BekbolatovNurdauletTaskController {
    private final BekbolatovNurdauletTaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BekbolatovNurdauletTaskResponseDto> create(
            @Valid @RequestBody BekbolatovNurdauletTaskRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<BekbolatovNurdauletTaskResponseDto>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BekbolatovNurdauletTaskStatus status,
            @PageableDefault(size = 5, sort = "deadline") Pageable pageable) {
        return ResponseEntity.ok(taskService.getAll(search, status, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<BekbolatovNurdauletTaskResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BekbolatovNurdauletTaskResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody BekbolatovNurdauletTaskRequestDto request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
