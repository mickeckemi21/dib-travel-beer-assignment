package io.mickeckemi21.beerassignment.service;

import io.mickeckemi21.beerassignment.client.PunkApiBeer;
import io.mickeckemi21.beerassignment.client.PunkApiClient;
import io.mickeckemi21.beerassignment.dto.BeerDto;
import io.mickeckemi21.beerassignment.exception.ClientException;
import io.mickeckemi21.beerassignment.exception.EntityNotFountException;
import io.mickeckemi21.beerassignment.mapper.BeerMapper;
import io.mickeckemi21.beerassignment.model.Beer;
import io.mickeckemi21.beerassignment.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.allOf;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private final PunkApiClient<CompletableFuture<PunkApiBeer>> asyncPunkApiClient;

    @Override
    public Collection<BeerDto> fill() {
        final Collection<BeerDto> beerDtoCollection = getAll();
        if (beerDtoCollection.size() != 10) {
            final Collection<CompletableFuture<PunkApiBeer>> completableFutures = new ArrayList<>();
            for (int i = 0; i < 10 - beerDtoCollection.size(); i++) {
                completableFutures.add(this.asyncPunkApiClient.getRandomBeer());
            }

            allOf(completableFutures
                    .toArray((CompletableFuture<PunkApiBeer>[]) new CompletableFuture[0]))
                    .join();

            completableFutures.forEach(punkApiBeer -> {
                try {
                    final Beer beer = this.beerMapper.punkApiBeerToBeer(punkApiBeer.get());
                    this.beerRepository.save(beer);
                    beerDtoCollection.add(this.beerMapper.beerToBeerDto(beer));
                } catch (InterruptedException | ExecutionException ex) {
                    throw new ClientException("Exception happened when trying to fetch data from external service", ex);
                }
            });
        }

        return beerDtoCollection;
    }

    @Override
    public BeerDto deleteByInternalId(String internalId) {
        final Beer beer = this.beerRepository.findByInternalId(internalId)
                .orElseThrow(() ->
                        new EntityNotFountException("Beer not found for provided internal id: " + internalId));
        final BeerDto beerDto = this.beerMapper.beerToBeerDto(beer);
        this.beerRepository.delete(beer);

        return beerDto;
    }

    @Override
    public BeerDto getByInternalId(String internalId) {
        final Beer beer = this.beerRepository.findByInternalId(internalId)
                .orElseThrow(() ->
                        new EntityNotFountException("Beer not found for provided internal id: " + internalId));

        return this.beerMapper.beerToBeerDto(beer);
    }

    @Override
    public Collection<BeerDto> getAll() {
        final Collection<BeerDto> beerDtoCollection = new ArrayList<>();
        final Iterable<Beer> beers = this.beerRepository.findAll();
        beers.forEach(beer -> beerDtoCollection.add(this.beerMapper.beerToBeerDto(beer)));

        return beerDtoCollection;
    }

}
