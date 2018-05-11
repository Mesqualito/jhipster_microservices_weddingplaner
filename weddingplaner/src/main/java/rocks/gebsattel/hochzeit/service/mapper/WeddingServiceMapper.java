package rocks.gebsattel.hochzeit.service.mapper;

import rocks.gebsattel.hochzeit.domain.*;
import rocks.gebsattel.hochzeit.service.dto.WeddingServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WeddingService and its DTO WeddingServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, AppUserMapper.class})
public interface WeddingServiceMapper extends EntityMapper<WeddingServiceDTO, WeddingService> {

    @Mapping(source = "businessAddress.id", target = "businessAddressId")
    @Mapping(source = "privateAddress.id", target = "privateAddressId")
    @Mapping(source = "appUser.id", target = "appUserId")
    WeddingServiceDTO toDto(WeddingService weddingService);

    @Mapping(source = "businessAddressId", target = "businessAddress")
    @Mapping(source = "privateAddressId", target = "privateAddress")
    @Mapping(source = "appUserId", target = "appUser")
    WeddingService toEntity(WeddingServiceDTO weddingServiceDTO);

    default WeddingService fromId(Long id) {
        if (id == null) {
            return null;
        }
        WeddingService weddingService = new WeddingService();
        weddingService.setId(id);
        return weddingService;
    }
}
