package kz.bekbolatovnurdaulet.taskmanagementsystem.mapper;

import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletCommentDtos.BekbolatovNurdauletCommentResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BekbolatovNurdauletCommentMapper {
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    BekbolatovNurdauletCommentResponseDto toDto(BekbolatovNurdauletComment comment);
}
