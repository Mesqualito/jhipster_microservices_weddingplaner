package rocks.gebsattel.hochzeit.service.mapper;

import rocks.gebsattel.hochzeit.domain.*;
import rocks.gebsattel.hochzeit.service.dto.PartyFoodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PartyFood and its DTO PartyFoodDTO.
 */
@Mapper(componentModel = "spring", uses = {AppUserMapper.class})
public interface PartyFoodMapper extends EntityMapper<PartyFoodDTO, PartyFood> {

    @Mapping(source = "owner.id", target = "ownerId")
    PartyFoodDTO toDto(PartyFood partyFood);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(target = "acceptedByHost", ignore = true)
    PartyFood toEntity(PartyFoodDTO partyFoodDTO);

    default PartyFood fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartyFood partyFood = new PartyFood();
        partyFood.setId(id);
        return partyFood;
    }
}
