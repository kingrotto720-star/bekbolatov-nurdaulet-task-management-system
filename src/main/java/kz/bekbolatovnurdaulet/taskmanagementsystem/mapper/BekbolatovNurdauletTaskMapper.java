package kz.bekbolatovnurdaulet.taskmanagementsystem.mapper;

import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletTaskDtos.BekbolatovNurdauletTaskResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BekbolatovNurdauletTaskMapper {
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectName", source = "project.name")
    @Mapping(target = "assignedUserId", source = "assignedUser.id")
    @Mapping(target = "assignedUserName", source = "assignedUser.name")
    BekbolatovNurdauletTaskResponseDto toDto(BekbolatovNurdauletTask task);
}
