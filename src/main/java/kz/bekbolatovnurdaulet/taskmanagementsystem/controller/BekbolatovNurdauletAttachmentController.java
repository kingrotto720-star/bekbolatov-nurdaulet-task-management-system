package kz.bekbolatovnurdaulet.taskmanagementsystem.controller;

import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletAttachmentResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.service.BekbolatovNurdauletAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class BekbolatovNurdauletAttachmentController {
    private final BekbolatovNurdauletAttachmentService attachmentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<BekbolatovNurdauletAttachmentResponseDto> upload(
            @RequestParam Long taskId, @RequestParam MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentService.upload(taskId, file));
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        var attachment = attachmentService.findEntity(id);
        Resource resource = attachmentService.download(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename(attachment.getFileName()).build().toString())
                .body(resource);
    }
}
