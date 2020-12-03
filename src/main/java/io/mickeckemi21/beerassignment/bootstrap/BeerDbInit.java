package io.mickeckemi21.beerassignment.bootstrap;

import io.mickeckemi21.beerassignment.client.PunkApiBeer;
import io.mickeckemi21.beerassignment.client.PunkApiClient;
import io.mickeckemi21.beerassignment.exception.DatabaseInitializationException;
import io.mickeckemi21.beerassignment.mapper.BeerMapper;
import io.mickeckemi21.beerassignment.model.Beer;
import io.mickeckemi21.beerassignment.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.allOf;

@Component
@RequiredArgsConstructor
public class BeerDbInit implements CommandLineRunner {

    private final PunkApiClient<CompletableFuture<PunkApiBeer>> asyncPunkApiClient;
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public void run(String... args) {
        final int n = 10;
        int i = 0;
        final Collection<CompletableFuture<PunkApiBeer>> completableFutures = new ArrayList<>();
        while (i < n) {
            completableFutures.add(asyncPunkApiClient.getRandomBeer());
            i++;
        }

        allOf(completableFutures
                .toArray((CompletableFuture<PunkApiBeer>[]) new CompletableFuture[0]))
                .join();

        completableFutures.forEach(punkApiBeerCompletableFuture -> {
            try {
                final Beer beer = this.beerMapper.punkApiBeerToBeer(punkApiBeerCompletableFuture.get());
                this.beerRepository.save(beer);
            } catch (Exception ex) {
                throw new DatabaseInitializationException("Exception occurred when tring to initialize database", ex);
            }
        });
    }

}
