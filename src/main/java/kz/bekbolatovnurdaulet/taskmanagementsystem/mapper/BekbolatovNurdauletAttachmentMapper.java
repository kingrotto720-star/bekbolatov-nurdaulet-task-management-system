package kz.bekbolatovnurdaulet.taskmanagementsystem.mapper;

import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletAttachmentResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BekbolatovNurdauletAttachmentMapper {
    @Mapping(target = "taskId", source = "task.id")
    BekbolatovNurdauletAttachmentResponseDto toDto(BekbolatovNurdauletAttachment attachment);
}
