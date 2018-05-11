package rocks.gebsattel.hochzeit.service.mapper;

import rocks.gebsattel.hochzeit.domain.*;
import rocks.gebsattel.hochzeit.service.dto.AppUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AppUser and its DTO AppUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {


    @Mapping(target = "weddingGuest", ignore = true)
    @Mapping(target = "weddingHost", ignore = true)
    @Mapping(target = "weddingService", ignore = true)
    @Mapping(target = "foodOwners", ignore = true)
    @Mapping(target = "eventOwners", ignore = true)
    @Mapping(target = "messageOwners", ignore = true)
    @Mapping(target = "foodMemberUsers", ignore = true)
    @Mapping(target = "receivedMessages", ignore = true)
    AppUser toEntity(AppUserDTO appUserDTO);

    default AppUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppUser appUser = new AppUser();
        appUser.setId(id);
        return appUser;
    }
}
