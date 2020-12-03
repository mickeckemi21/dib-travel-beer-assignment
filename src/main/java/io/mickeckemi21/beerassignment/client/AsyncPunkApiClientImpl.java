package io.mickeckemi21.beerassignment.client;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncPunkApiClientImpl implements PunkApiClient<CompletableFuture<PunkApiBeer>> {

    private final PunkApiClient<PunkApiBeer> client;

    @Async
    @Override
    public CompletableFuture<PunkApiBeer> getRandomBeer() {
        return CompletableFuture.completedFuture(this.client.getRandomBeer());
    }

}
