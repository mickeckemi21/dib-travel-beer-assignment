package io.mickeckemi21.beerassignment.repository;

import io.mickeckemi21.beerassignment.model.Beer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeerRepository extends CrudRepository<Beer, Long> {

    Optional<Beer> findByInternalId(String internalId);

}
