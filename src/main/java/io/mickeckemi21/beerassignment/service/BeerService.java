package io.mickeckemi21.beerassignment.service;

import io.mickeckemi21.beerassignment.dto.BeerDto;

import java.util.Collection;

public interface BeerService {

    Collection<BeerDto> fill();

    BeerDto deleteByInternalId(String internalId);

    BeerDto getByInternalId(String internalId);

    Collection<BeerDto> getAll();

}
