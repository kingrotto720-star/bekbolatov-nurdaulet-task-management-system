package kz.bekbolatovnurdaulet.taskmanagementsystem.mapper;

import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletProjectDtos.BekbolatovNurdauletProjectResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletProject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BekbolatovNurdauletProjectMapper {
    BekbolatovNurdauletProjectResponseDto toDto(BekbolatovNurdauletProject project);
}
