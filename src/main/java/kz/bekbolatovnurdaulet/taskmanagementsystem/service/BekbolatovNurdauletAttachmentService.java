package kz.bekbolatovnurdaulet.taskmanagementsystem.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletAttachmentResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletAttachment;
import kz.bekbolatovnurdaulet.taskmanagementsystem.exception.BekbolatovNurdauletBadRequestException;
import kz.bekbolatovnurdaulet.taskmanagementsystem.exception.BekbolatovNurdauletResourceNotFoundException;
import kz.bekbolatovnurdaulet.taskmanagementsystem.mapper.BekbolatovNurdauletAttachmentMapper;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletAttachmentRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BekbolatovNurdauletAttachmentService {
    private final BekbolatovNurdauletAttachmentRepository attachmentRepository;
    private final BekbolatovNurdauletTaskRepository taskRepository;
    private final BekbolatovNurdauletAttachmentMapper attachmentMapper;

    @Value("${app.upload-dir}")
    private String uploadDir;

    public BekbolatovNurdauletAttachmentResponseDto upload(Long taskId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new BekbolatovNurdauletBadRequestException("File is empty");
        }
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BekbolatovNurdauletResourceNotFoundException("Task not found"));
        try {
            Files.createDirectories(Path.of(uploadDir));
            String originalName = Path.of(file.getOriginalFilename() == null ? "file" : file.getOriginalFilename())
                    .getFileName().toString();
            String storedName = UUID.randomUUID() + "-" + originalName;
            Path target = Path.of(uploadDir).resolve(storedName).normalize();
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            var attachment = BekbolatovNurdauletAttachment.builder()
                    .fileName(originalName)
                    .filePath(target.toString())
                    .uploadedAt(LocalDateTime.now())
                    .task(task)
                    .build();
            log.info("Uploaded file {} for task {}", originalName, taskId);
            return attachmentMapper.toDto(attachmentRepository.save(attachment));
        } catch (IOException ex) {
            throw new BekbolatovNurdauletBadRequestException("Could not upload file: " + ex.getMessage());
        }
    }

    public BekbolatovNurdauletAttachment findEntity(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new BekbolatovNurdauletResourceNotFoundException("Attachment not found with id " + id));
    }

    public Resource download(Long id) {
        var attachment = findEntity(id);
        try {
            Resource resource = new UrlResource(Path.of(attachment.getFilePath()).toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new BekbolatovNurdauletResourceNotFoundException("File is not readable");
            }
            return resource;
        } catch (MalformedURLException ex) {
            throw new BekbolatovNurdauletBadRequestException("Invalid file path");
        }
    }
}
