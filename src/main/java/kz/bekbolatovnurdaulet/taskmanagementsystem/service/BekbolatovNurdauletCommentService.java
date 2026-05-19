package kz.bekbolatovnurdaulet.taskmanagementsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletCommentDtos.BekbolatovNurdauletCommentRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletCommentDtos.BekbolatovNurdauletCommentResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletComment;
import kz.bekbolatovnurdaulet.taskmanagementsystem.exception.BekbolatovNurdauletResourceNotFoundException;
import kz.bekbolatovnurdaulet.taskmanagementsystem.mapper.BekbolatovNurdauletCommentMapper;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletCommentRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletTaskRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BekbolatovNurdauletCommentService {
    private final BekbolatovNurdauletCommentRepository commentRepository;
    private final BekbolatovNurdauletTaskRepository taskRepository;
    private final BekbolatovNurdauletUserRepository userRepository;
    private final BekbolatovNurdauletCommentMapper commentMapper;

    public BekbolatovNurdauletCommentResponseDto create(BekbolatovNurdauletCommentRequestDto request) {
        var task = taskRepository.findById(request.taskId())
                .orElseThrow(() -> new BekbolatovNurdauletResourceNotFoundException("Task not found"));
        var user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BekbolatovNurdauletResourceNotFoundException("User not found"));
        var comment = BekbolatovNurdauletComment.builder()
                .message(request.message())
                .createdAt(LocalDateTime.now())
                .task(task)
                .user(user)
                .build();
        return commentMapper.toDto(commentRepository.save(comment));
    }

    public List<BekbolatovNurdauletCommentResponseDto> getByTaskId(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new BekbolatovNurdauletResourceNotFoundException("Task not found with id " + taskId);
        }
        return commentRepository.findByTaskIdOrderByCreatedAtDesc(taskId)
                .stream().map(commentMapper::toDto).toList();
    }
}
