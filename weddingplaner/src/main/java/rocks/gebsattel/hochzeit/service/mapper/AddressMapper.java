package rocks.gebsattel.hochzeit.service.mapper;

import rocks.gebsattel.hochzeit.domain.*;
import rocks.gebsattel.hochzeit.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {


    @Mapping(target = "guestPrivate", ignore = true)
    @Mapping(target = "guestBusiness", ignore = true)
    @Mapping(target = "serviceBusiness", ignore = true)
    @Mapping(target = "servicePrivate", ignore = true)
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
