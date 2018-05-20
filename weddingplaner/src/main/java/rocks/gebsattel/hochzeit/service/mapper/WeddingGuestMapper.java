package rocks.gebsattel.hochzeit.service.mapper;

import rocks.gebsattel.hochzeit.domain.*;
import rocks.gebsattel.hochzeit.service.dto.WeddingGuestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WeddingGuest and its DTO WeddingGuestDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, AppUserMapper.class})
public interface WeddingGuestMapper extends EntityMapper<WeddingGuestDTO, WeddingGuest> {

    @Mapping(source = "privateAddress.id", target = "privateAddressId")
    @Mapping(source = "businessAddress.id", target = "businessAddressId")
    @Mapping(source = "appUser.id", target = "appUserId")
    WeddingGuestDTO toDto(WeddingGuest weddingGuest);

    @Mapping(source = "privateAddressId", target = "privateAddress")
    @Mapping(source = "businessAddressId", target = "businessAddress")
    @Mapping(source = "appUserId", target = "appUser")
    WeddingGuest toEntity(WeddingGuestDTO weddingGuestDTO);

    default WeddingGuest fromId(Long id) {
        if (id == null) {
            return null;
        }
        WeddingGuest weddingGuest = new WeddingGuest();
        weddingGuest.setId(id);
        return weddingGuest;
    }
}
