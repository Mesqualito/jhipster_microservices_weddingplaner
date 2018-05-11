package rocks.gebsattel.hochzeit.repository;

import rocks.gebsattel.hochzeit.domain.PartyFood;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the PartyFood entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyFoodRepository extends JpaRepository<PartyFood, Long> {
    @Query("select distinct party_food from PartyFood party_food left join fetch party_food.memberUsers")
    List<PartyFood> findAllWithEagerRelationships();

    @Query("select party_food from PartyFood party_food left join fetch party_food.memberUsers where party_food.id =:id")
    PartyFood findOneWithEagerRelationships(@Param("id") Long id);

}
