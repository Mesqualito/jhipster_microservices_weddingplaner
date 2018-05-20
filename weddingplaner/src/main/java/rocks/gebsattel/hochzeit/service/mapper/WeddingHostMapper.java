package rocks.gebsattel.hochzeit.service.mapper;

import rocks.gebsattel.hochzeit.domain.*;
import rocks.gebsattel.hochzeit.service.dto.WeddingHostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WeddingHost and its DTO WeddingHostDTO.
 */
@Mapper(componentModel = "spring", uses = {AppUserMapper.class, PartyFoodMapper.class})
public interface WeddingHostMapper extends EntityMapper<WeddingHostDTO, WeddingHost> {

    @Mapping(source = "appUser.id", target = "appUserId")
    @Mapping(source = "foodProposalAcceptHost.id", target = "foodProposalAcceptHostId")
    WeddingHostDTO toDto(WeddingHost weddingHost);

    @Mapping(source = "appUserId", target = "appUser")
    @Mapping(source = "foodProposalAcceptHostId", target = "foodProposalAcceptHost")
    WeddingHost toEntity(WeddingHostDTO weddingHostDTO);

    default WeddingHost fromId(Long id) {
        if (id == null) {
            return null;
        }
        WeddingHost weddingHost = new WeddingHost();
        weddingHost.setId(id);
        return weddingHost;
    }
}
