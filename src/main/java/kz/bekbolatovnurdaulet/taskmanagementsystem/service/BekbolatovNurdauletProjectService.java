package kz.bekbolatovnurdaulet.taskmanagementsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletProjectDtos.BekbolatovNurdauletProjectRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletProjectDtos.BekbolatovNurdauletProjectResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletProject;
import kz.bekbolatovnurdaulet.taskmanagementsystem.exception.BekbolatovNurdauletResourceNotFoundException;
import kz.bekbolatovnurdaulet.taskmanagementsystem.mapper.BekbolatovNurdauletProjectMapper;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BekbolatovNurdauletProjectService {
    private final BekbolatovNurdauletProjectRepository projectRepository;
    private final BekbolatovNurdauletProjectMapper projectMapper;

    public BekbolatovNurdauletProjectResponseDto create(BekbolatovNurdauletProjectRequestDto request) {
        var project = BekbolatovNurdauletProject.builder()
                .name(request.name())
                .description(request.description())
                .createdAt(LocalDateTime.now())
                .build();
        return projectMapper.toDto(projectRepository.save(project));
    }

    public List<BekbolatovNurdauletProjectResponseDto> getAll() {
        return projectRepository.findAll().stream().map(projectMapper::toDto).toList();
    }

    public BekbolatovNurdauletProjectResponseDto getById(Long id) {
        return projectMapper.toDto(projectRepository.findById(id).orElseThrow(() -> notFound(id)));
    }

    public BekbolatovNurdauletProjectResponseDto update(Long id, BekbolatovNurdauletProjectRequestDto request) {
        var project = projectRepository.findById(id).orElseThrow(() -> notFound(id));
        project.setName(request.name());
        project.setDescription(request.description());
        return projectMapper.toDto(projectRepository.save(project));
    }

    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw notFound(id);
        }
        projectRepository.deleteById(id);
    }

    private BekbolatovNurdauletResourceNotFoundException notFound(Long id) {
        return new BekbolatovNurdauletResourceNotFoundException("Project not found with id " + id);
    }
}
