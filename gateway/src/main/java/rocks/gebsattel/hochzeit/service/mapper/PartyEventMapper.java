package rocks.gebsattel.hochzeit.service.mapper;

import rocks.gebsattel.hochzeit.domain.*;
import rocks.gebsattel.hochzeit.service.dto.PartyEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PartyEvent and its DTO PartyEventDTO.
 */
@Mapper(componentModel = "spring", uses = {AppUserMapper.class})
public interface PartyEventMapper extends EntityMapper<PartyEventDTO, PartyEvent> {

    @Mapping(source = "owner.id", target = "ownerId")
    PartyEventDTO toDto(PartyEvent partyEvent);

    @Mapping(source = "ownerId", target = "owner")
    PartyEvent toEntity(PartyEventDTO partyEventDTO);

    default PartyEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartyEvent partyEvent = new PartyEvent();
        partyEvent.setId(id);
        return partyEvent;
    }
}
