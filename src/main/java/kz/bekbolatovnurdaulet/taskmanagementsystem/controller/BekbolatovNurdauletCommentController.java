package kz.bekbolatovnurdaulet.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import java.util.List;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletCommentDtos.BekbolatovNurdauletCommentRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletCommentDtos.BekbolatovNurdauletCommentResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.service.BekbolatovNurdauletCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class BekbolatovNurdauletCommentController {
    private final BekbolatovNurdauletCommentService commentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<BekbolatovNurdauletCommentResponseDto> create(
            @Valid @RequestBody BekbolatovNurdauletCommentRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(request));
    }

    @GetMapping("/task/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<BekbolatovNurdauletCommentResponseDto>> getByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getByTaskId(taskId));
    }
}
