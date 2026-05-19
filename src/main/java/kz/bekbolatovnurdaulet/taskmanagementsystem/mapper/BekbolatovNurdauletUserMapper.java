package kz.bekbolatovnurdaulet.taskmanagementsystem.mapper;

import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletUserResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BekbolatovNurdauletUserMapper {
    @Mapping(target = "role", source = "role.name")
    BekbolatovNurdauletUserResponseDto toDto(BekbolatovNurdauletUser user);
}
