package kz.bekbolatovnurdaulet.taskmanagementsystem.service;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletTaskDtos.BekbolatovNurdauletTaskRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletTaskDtos.BekbolatovNurdauletTaskResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTask;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTaskStatus;
import kz.bekbolatovnurdaulet.taskmanagementsystem.exception.BekbolatovNurdauletResourceNotFoundException;
import kz.bekbolatovnurdaulet.taskmanagementsystem.mapper.BekbolatovNurdauletTaskMapper;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletProjectRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletTaskRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BekbolatovNurdauletTaskService {
    private final BekbolatovNurdauletTaskRepository taskRepository;
    private final BekbolatovNurdauletProjectRepository projectRepository;
    private final BekbolatovNurdauletUserRepository userRepository;
    private final BekbolatovNurdauletTaskMapper taskMapper;
    private final BekbolatovNurdauletAsyncService asyncService;

    public BekbolatovNurdauletTaskResponseDto create(BekbolatovNurdauletTaskRequestDto request) {
        var project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new BekbolatovNurdauletResourceNotFoundException("Project not found"));
        var user = userRepository.findById(request.assignedUserId())
                .orElseThrow(() -> new BekbolatovNurdauletResourceNotFoundException("Assigned user not found"));
        var task = BekbolatovNurdauletTask.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .priority(request.priority())
                .deadline(request.deadline())
                .createdAt(LocalDateTime.now())
                .project(project)
                .assignedUser(user)
                .build();
        task = taskRepository.save(task);
        asyncService.sendTaskCreatedNotification(task.getTitle(), user.getEmail());
        asyncService.generateTaskReport(task.getId());
        log.info("Created task {} for project {}", task.getId(), project.getId());
        return taskMapper.toDto(task);
    }

    public Page<BekbolatovNurdauletTaskResponseDto> getAll(String search, BekbolatovNurdauletTaskStatus status, Pageable pageable) {
        return taskRepository.findAll(specification(search, status), pageable).map(taskMapper::toDto);
    }

    public BekbolatovNurdauletTaskResponseDto getById(Long id) {
        return taskMapper.toDto(taskRepository.findById(id).orElseThrow(() -> notFound(id)));
    }

    public BekbolatovNurdauletTaskResponseDto update(Long id, BekbolatovNurdauletTaskRequestDto request) {
        var task = taskRepository.findById(id).orElseThrow(() -> notFound(id));
        var project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new BekbolatovNurdauletResourceNotFoundException("Project not found"));
        var user = userRepository.findById(request.assignedUserId())
                .orElseThrow(() -> new BekbolatovNurdauletResourceNotFoundException("Assigned user not found"));
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setDeadline(request.deadline());
        task.setProject(project);
        task.setAssignedUser(user);
        return taskMapper.toDto(taskRepository.save(task));
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw notFound(id);
        }
        taskRepository.deleteById(id);
    }

    private Specification<BekbolatovNurdauletTask> specification(String search, BekbolatovNurdauletTaskStatus status) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (search != null && !search.isBlank()) {
                String like = "%" + search.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), like),
                        cb.like(cb.lower(root.get("description")), like)));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private BekbolatovNurdauletResourceNotFoundException notFound(Long id) {
        return new BekbolatovNurdauletResourceNotFoundException("Task not found with id " + id);
    }
}
